import { createApp } from "vue";
import App from "./App";
import router from "./router";
import DropdownMenu from "./components/DropdownMenu";
import store from "./store";

createApp(App)
    .use(router)
    .use(store)
    .component('dropdown-menu', DropdownMenu)
    .mount("#app");