import ClientKeeperClient from '../api/clientKeeperClient';

document.addEventListener('DOMContentLoaded', async () => {
    const client = new ClientKeeperClient();
    const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
    const signOutButton = document.getElementById('signOutButton');

    // Fetch and display orders
    async function loadOrders(orders) {
        ordersTableBody.innerHTML = '';

        if (Array.isArray(orders)) {
            orders.forEach(order => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${order.clientName}</td>
                    <td>${order.clientId}</td>
                    <td>${order.orderId}</td>
                    <td>${order.purchaseDate}</td>
                    <td>${order.deliveredDate}</td>
                    <td>${order.shippingService}</td>
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

    // Load all delivered orders on page load
    async function loadAllDeliveredOrders() {
        const response = await client.getDeliveredOrders();
        console.log('Delivered Orders response:', response);
        loadOrders(response.orders);
    }

    // Sign out button click handler
    signOutButton.addEventListener('click', async () => {
        await client.logout();
        window.location.href = 'index.html';
    });

    // Load orders on page load
    loadAllDeliveredOrders();
});
