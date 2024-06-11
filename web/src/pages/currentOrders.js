import ClientKeeperClient from '../api/clientKeeperClient';

document.addEventListener('DOMContentLoaded', async () => {
    const client = new ClientKeeperClient();
    const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
    const addOrderButton = document.getElementById('addOrderButton');
    const signOutButton = document.getElementById('signOutButton');

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
                <td>${order.clientId}</td>
                <td>${order.orderId}</td>
                <td>${order.purchaseDate}</td>
                <td>${order.shipped ? '✅' : '❌'}</td>
                <td>${order.shippingService}</td>
                <td>${order.expectedDate}</td>
                <td>${order.trackingNumber}</td>
            `;

            ordersTableBody.appendChild(row);
        });
    } else {
        console.error('Expected an array of orders, but got:', orders);
    }
}

    // Add order button
    addOrderButton.addEventListener('click', () => {
        // Logic for adding an order
        alert('Add Order button clicked');
    });

    // Sign out button click handler
    signOutButton.addEventListener('click', async () => {
        await client.logout();
        window.location.href = 'index.html';
    });

    // Load orders on page load
    loadOrders();
});