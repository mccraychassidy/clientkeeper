import ClientKeeperClient from '../api/clientKeeperClient';

document.addEventListener('DOMContentLoaded', async () => {
    const client = new ClientKeeperClient();
    const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
    const addOrderButton = document.getElementById('addOrderButton');
    const signOutButton = document.getElementById('signOutButton');
    const addOrderModal = document.getElementById('addOrderModal');
    const closeModal = document.getElementById('closeModal');
    const addOrderForm = document.getElementById('addOrderForm');

    // Fetch and display orders
    async function loadOrders() {
        const response = await client.getUndeliveredOrders();
        console.log('Orders response:', response);

        const orders = response.orders;
        console.log('Orders:', orders);

        ordersTableBody.innerHTML = '';

        if (Array.isArray(orders)) {
            orders.forEach(order => {
                const row = document.createElement('tr');

            row.innerHTML = `
                <td>${order.clientName}</td>
                <td>${order.clientId}</td>
                <td>${order.orderId}</td>
                <td>${order.purchaseDate}</td>
                <td>${order.shipped ? '✅' : '❌'}</td>
                <td>${order.shippingService}</td>
                <td>${order.expectedDate}</td>
                <td>${order.trackingNumber}</td>
                <td>${order.item}</td>
                <td>${order.reference}</td>
            `;

            ordersTableBody.appendChild(row);
        });
    } else {
        console.error('Expected an array of orders, but got:', orders);
    }
}

// Open modal
addOrderButton.addEventListener('click', () => {
    addOrderModal.style.display = 'block';
});

// Close modal
closeModal.addEventListener('click', () => {
    addOrderModal.style.display = 'none';
});

// Close modal when clicking outside of it
window.addEventListener('click', (event) => {
    if (event.target == addOrderModal) {
        addOrderModal.style.display = 'none';
    }
});

// Add order form submission
addOrderForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(addOrderForm);
    const newOrder = {
        clientName: formData.get('clientName'),
        clientId: formData.get('clientId'),
        purchaseDate: formData.get('purchaseDate'),
        shipped: formData.get('shipped') === 'on',
        shippingService: formData.get('shippingService'),
        expectedDate: formData.get('expectedDate'),
        trackingNumber: formData.get('trackingNumber'),
        item: formData.get('item'),
        reference: formData.get('reference')
    };

    console.log('New Order Data:', newOrder);

    try {
        await client.createOrder(newOrder);
        addOrderModal.style.display = 'none';
        loadOrders(); // Reload orders after adding a new one
    } catch (error) {
        console.error('Error creating order:', error);
    }
});

// Sign out button click handler
signOutButton.addEventListener('click', async () => {
    await client.logout();
    window.location.href = 'index.html';
});

// Load orders on page load
loadOrders();
});