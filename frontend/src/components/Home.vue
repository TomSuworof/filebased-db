<template>
  <div>
    <div class="header">
      <div>
        <h1>{{ currentDatabase }}.csv</h1>
      </div>
    </div>
    <div class="search-bar">
      <div class="row">
        <input type="text" placeholder="Search" v-model="searchQuery"/>
        <button @click="search(searchQuery)" title="Search">🔎</button>
        <button v-show="showDeleteByQuery" @click="deleteByQuery(searchQuery)">❌ Delete found items</button>
      </div>
    </div>
    <div class="items">
      <table class="table">
        <tr class="table-row">
          <th>ID</th>
          <th>Name</th>
          <th>Amount available</th>
          <th>Price</th>
          <th>Color</th>
          <th>Refurbished</th>
        </tr>
        <tr v-for="item in items" v-bind:key="item.id" class="item-row">
          <td>{{ item.id }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.amountAvailable }}</td>
          <td>${{ item.price }}</td>
          <td>{{ item.color }}</td>
          <td>
            <div class="item-refurbished">
              {{ item.refurbished }}
            </div>
            <div class="item-actions">
              <button title="Edit item" @click="openItemEditor(item, editItem)">✏</button>
              <button title="Delete item" @click="deleteItem(item)">❌</button>
            </div>
          </td>
        </tr>
        <tr v-show="!showItemEditor">
          <td>
            <button title="New item" @click="openItemEditor({}, addItem)">➕</button>
          </td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr v-show="showItemEditor">
          <td>
            <input class="table-input" type="number" v-model="newItem.id" placeholder="ID">
          </td>
          <td>
            <input class="table-input" type="text" v-model="newItem.name" placeholder="Name">
          </td>
          <td>
            <input class="table-input" type="number" v-model="newItem.amountAvailable" placeholder="Amount available">
          </td>
          <td>
            <input class="table-input" type="number" v-model="newItem.price" placeholder="Price">
          </td>
          <td>
            <input class="table-input" type="text" v-model="newItem.color" placeholder="Color">
          </td>
          <td>
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="true" v-model="newItem.refurbished"
                     id="refurbished-checkbox">
              <label class="form-check-label" for="refurbished-checkbox">Refurbished</label>
            </div>
            <div class="item-function-button">
              <div>
                <button @click="functionForItem(newItem)">✔</button>
              </div>
              <div class="error-message">
                {{ errorMessage }}
              </div>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import ItemService from "../services/ItemsService";

export default {
  name: "Home",
  data() {
    return {
      searchQuery: undefined,
      showDeleteByQuery: false,

      items: [],

      showItemEditor: false,
      oldItemId: undefined,
      newItem: {},
      errorMessage: undefined,
      functionForItem: undefined,
    }
  },
  computed: {
    currentDatabase() {
      return this.$store.state.currentDatabase;
    }
  },
  methods: {
    search: function (searchQuery) {
      ItemService.findItems(searchQuery)
          .then(response => {
            this.items = response.data;
            this.showDeleteByQuery = true;
          })
          .catch(err => {
            console.log(err.response.data.error);
          });
    },
    deleteByQuery: function (searchQuery) {
      ItemService.deleteItems(searchQuery);
      this.$router.go(0); // refreshing page
    },
    getAllItems: function () {
      ItemService.getAllItems()
          .then(response => {
            this.items = response.data;
          })
          .catch(err => {
            console.log(err.response.data.error);
          })
    },
    openItemEditor: function (itemIn, functionForItem) {
      this.newItem = itemIn
      this.showItemEditor = true;
      this.functionForItem = functionForItem;
      this.oldItemId = itemIn.id;
    },
    addItem: function (item) {
      ItemService.addItem(item)
          .then(() => {
            this.$router.go(0); // refreshing page
          })
          .catch(err => {
            this.errorMessage = err.response.data.error.replace('[', '').replace(']', '');
          });
    },
    editItem: function (item) {
      ItemService.editItem(this.oldItemId, item)
          .then(() => {
            this.showItemEditor = false
            this.$router.go(0); // refreshing page
          })
          .catch(err => {
            this.errorMessage = err.response.data.error.replace('[', '').replace(']', '');
          });
    },
    deleteItem: function (item) {
      ItemService.deleteItem(item);
      this.$router.go(0);
    }
  },
  created() {
    this.getAllItems();
  }
}
</script>

<style scoped>
button {
  border: none;
  background: white;
}

.search-bar {
  padding: 10pt 0;
}

.row {
  padding: 11pt;
  justify-content: start;
}

.table {
  table-layout: fixed;
  align-self: center;
  border-collapse: collapse;
  width: 100%;
}

.item-actions {
  display: none;
}

.item-row:hover .item-actions, .item-refurbished {
  /* fixes bugs with floating heights of table rows */
  line-height: 100%;
  display: inline-block;
}

.table-input {
  width: 90%;
}

.form-check, .item-function-button {
  margin: 0 5pt 0;
  display: inline-block;
}
</style>