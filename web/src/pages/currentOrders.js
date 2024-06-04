import BirthdayClient from '../api/clientKeeperClient';

document.addEventListener('DOMContentLoaded', async () => {
    const client = new BirthdayClient();
    const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
    const addOrderButton = document.getElementById('addOrderButton');
    const signOutButton = document.getElementById('signOutButton');

    // Fetch and display orders
    async function loadOrders() {
        const orders = await client.getOrders(); // This is just a placeholder --- Assuming getOrders() is a method to fetch orders
        ordersTableBody.innerHTML = ''; // Clear any existing rows

        orders.forEach(order => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${order.client}</td>
                <td>${order.orderNumber}</td>
                <td>${order.purchased}</td>
                <td>${order.shipped ? '✅' : '❌'}</td>
                <td>${order.service}</td>
                <td>${order.expectedDelivery}</td>
                <td>${order.trackingNumber}</td>
            `;

            ordersTableBody.appendChild(row);
        });
    }

    // Add order button click handler
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
