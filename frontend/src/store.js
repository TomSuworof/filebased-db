import Vuex from 'vuex'

let currentDatabase = localStorage.getItem('currentDatabase');

const store = new Vuex.Store({
    state: {
        currentDatabase: currentDatabase
    },
    actions: {
        addDatabase({ commit }, database) {
            commit('addDatabase', database);
            localStorage.setItem('currentDatabase', database);
        },
        // removeDatabase({ commit }, databasesInfo) {
        //     commit('removeDatabase', databasesInfo);
        // },
        switchToDatabase({ commit }, databasesInfo) {
            commit('switchToDatabase', databasesInfo);
            localStorage.setItem('currentDatabase', databasesInfo.database);
        }
    },
    mutations: {
        addDatabase(state, database) {
            state.currentDatabase = database;
        },
        // removeDatabase(state, remainedDatabases) {
        //     state.currentDatabase = remainedDatabases[0];
        // },
        switchToDatabase(state, databasesInfo) {
            if (databasesInfo.database in databasesInfo.allDatabases) {
                state.currentDatabase = databasesInfo.database;
            }
        }
    }
});

export default store;