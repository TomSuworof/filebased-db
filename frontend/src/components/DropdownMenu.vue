<template>
  <div>
    <div class="dropdown">
      <span>Backup download</span>
      <div class="dropdown-content">
        <div class="dropdown-menu-item">
          <a title="Download CSV backup" href="#" @click="getBackupFile()">Download CSV backup</a>
        </div>
        <div class="dropdown-menu-item">
          <a title="Download Excel view" href="#" @click="getBackupFileXLSX()">Download Excel view</a>
        </div>
      </div>
    </div>
    <div class="dropdown">
      <span>Backup upload</span>
      <div class="dropdown-content">
        <div class="dropdown-menu-item">
          <p>Upload backup file CSV</p>
          <input ref="file" type="file" accept=".csv" @change="uploadBackup"/>
        </div>
      </div>
    </div>
    <div class="dropdown">
      <span>Databases</span>
      <div class="dropdown-content">
        <div class="dropdown-menu-item">
          <table>
            <tr v-for="database in allDatabases" v-bind:key="database.id" class="database-row">
              <td v-show="database === currentDatabase">
                <strong>{{ database }}</strong>
              </td>
              <td v-show="database !== currentDatabase">
                <a href="#" @click="switchToDatabase(database)">{{ database }}</a>
              </td>
              <td v-show="database !== currentDatabase">
                <button @click="removeDatabase(database)" title="Delete database">❌</button>
              </td>
            </tr>
          </table>
        </div>
        <div v-if="!showDatabaseEditor">
          <button @click="openDatabaseEditor()">➕ Add new</button>
        </div>
        <div v-if="showDatabaseEditor">
          <input v-model="newDatabaseName" name="name" required placeholder="Name"/>
          <button @click="addDatabase(newDatabaseName)">✔</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UtilsService from "../services/UtilsService";
import DatabasesService from "../services/DatabasesService";

export default {
  name: "DropdownMenu",
  data() {
    return {
      allDatabases: [],

      showDatabaseEditor: false,
      newDatabaseName: undefined,
    }
  },
  computed: {
    currentDatabase() {
      return this.$store.state.currentDatabase;
    }
  },
  methods: {
    getBackupFile: function () {
      UtilsService.getBackupFile()
          .then(response => {
            let fileURL = window.URL.createObjectURL(new Blob([response.data]));
            let fileLink = document.createElement('a');

            fileLink.href = fileURL;
            fileLink.setAttribute('download', 'backup.csv');
            document.body.appendChild(fileLink);

            fileLink.click();
          });
    },
    getBackupFileXLSX: function () {
      UtilsService.getBackupFileXLSX()
          .then(response => {
            let fileURL = window.URL.createObjectURL(new Blob([response.data]));
            let fileLink = document.createElement('a');

            fileLink.href = fileURL;
            fileLink.setAttribute('download', 'filebased-db.xlsx');
            document.body.appendChild(fileLink);

            fileLink.click();
          });
    },
    uploadBackup: function () {
      let backupFile = this.$refs.file.files[0];
      let formData = new FormData();
      formData.append('file', backupFile);
      UtilsService.uploadBackupFile(formData);
      this.$router.go(0); // refreshing page
    },
    openDatabaseEditor: function () {
      this.showDatabaseEditor = true;
    },
    getAllDatabases: function () {
      DatabasesService.getAllDatabases()
          .then(response => {
            this.allDatabases = response.data;
          });
    },
    addDatabase: function (databaseName) {
      DatabasesService.addDatabase(databaseName);
      this.$store.dispatch('addDatabase', databaseName);
      this.$router.go(0); // refreshing page
    },
    removeDatabase: function (databaseName) {
      DatabasesService.removeDatabase(databaseName);
      this.$router.go(0); // refreshing page
    },
    switchToDatabase: function (databaseName) {
      DatabasesService.switchToDatabase(databaseName);
      this.$store.dispatch('switchToDatabase', {
        database: databaseName,
        allDatabases: this.allDatabases.flat()
      });
      this.$router.go(0); // refreshing page
    }
  },
  created() {
    this.getAllDatabases();
  }
}
</script>

<style scoped>
button {
  border: none;
  background: transparent;
}

.dropdown {
  display: inline-block;
  padding: 0 10pt 0;
  position: relative;
}

.dropdown-content {
  border-radius: 10px;
  display: none;
  position: absolute;
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(10px);
  min-width: 160px;
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
  padding: 12px 16px;
  z-index: 1;
}

.dropdown-menu-item {
  padding: 5pt;
}

tr.database-row > td {
  padding-bottom: 10pt;
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>