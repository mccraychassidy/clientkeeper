import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteHeader', 'createButton', 'createUserInfoForHeader'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new ClientKeeperClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteHeader = this.createSiteHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteHeader);
    }

    createSiteHeader(currentUser) {
        const headerContainer = document.createElement('div');
        headerContainer.classList.add('header-container');

        const leftContainer = document.createElement('div');
        leftContainer.classList.add('left-container');
        const homeButton = this.createButton('Request A Demo', 'demo.html', 'button');
        leftContainer.appendChild(homeButton);

        const centerContainer = document.createElement('div');
        centerContainer.classList.add('center-container');
        const aboutButton = this.createButton('About', 'about.html', 'button');
        centerContainer.appendChild(aboutButton);

        const rightContainer = this.createUserInfoForHeader(currentUser);

        headerContainer.appendChild(leftContainer);
        headerContainer.appendChild(centerContainer);
        headerContainer.appendChild(rightContainer);

        return headerContainer;
    }

    createUserInfoForHeader(currentUser) {
        const rightContainer = document.createElement('div');
        rightContainer.classList.add('right-container');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        rightContainer.appendChild(childContent);

        return rightContainer;
    }

    createLoginButton() {
        return this.createButton('Login / Sign Up', '#', 'button', this.client.login.bind(this.client));
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, '#', 'button', this.client.logout.bind(this.client));
    }

    createButton(text, href, className, clickHandler) {
        const button = document.createElement('a');
        button.classList.add(className);
        button.href = href;
        button.innerText = text;

        if (clickHandler) {
            button.addEventListener('click', async (event) => {
                event.preventDefault();
                await clickHandler();
            });
        }

        return button;
    }
}
