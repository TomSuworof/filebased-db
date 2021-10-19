<template>
  <div class="item-editor">
    <div class="row">
      <h1>Item editor</h1>
      <button @click="onclosebutton()">❌</button>
    </div>
    <div class="item-form">
      <form>
        <div>
          <input type="number" v-model="item.id" placeholder="ID" required>
        </div>
        <div>
          <input type="text" v-model="item.name" placeholder="Name" required>
        </div>
        <div>
          <input type="number" v-model="item.amountAvailable" placeholder="Amount available" required>
        </div>
        <div>
          <input type="number" v-model="item.price" placeholder="Price" required>
        </div>
        <div>
          <input type="text" v-model="item.color" placeholder="Color" required>
        </div>
        <div>
          <input type="checkbox" v-model="item.refurbished" id="refurbished-checkbox">
          <label for="refurbished-checkbox">Refurbished</label>
        </div>
      </form>
      <div>
        <button @click="addItem()">Submit</button>
      </div>
      <div class="error-message">
        {{ errorMessage }}
      </div>
    </div>
  </div>
</template>

<script>
import ItemService from "../services/ItemsService";

export default {
  props: ['onclosebutton', 'itemIn'],
  name: "ItemEditor",
  data() {
    return {
      item: {},
      errorMessage: undefined,
    }
  },
  methods: {
    addItem: function () {
      ItemService.addItem(this.item).catch(err => {
        if (err.response.status === 404) {
          this.onclosebutton(); // closing editor
          this.$router.go(0); // reloading page
        } else if (err.response.status === 422) {
          this.errorMessage = err.message;
        }
      });
    }
  },
  created() {
    this.item = this.itemIn;
  }
}
</script>

<style scoped>
.row {
  justify-content: space-between;
  padding: 0 15pt 0;
}

.item-editor {
  height: 300px;
  width: 300px;
  background: lightgrey;
}

button {
  border: none;
  background: none;
}

.item-form {
  text-align: center;
}
</style>