import http from '../http-common';
import getDatabaseName from '../get_current_database';

class DatabasesService {
    getAllDatabases() {
        return http.get('/databases', { params: { 'databaseName': getDatabaseName() } });
    }

    addDatabase(databaseName) {
        return http.post('databases/add', {}, { params: { databaseName } });
    }

    removeDatabase(databaseName) {
        return http.post('databases/remove', {}, { params: { databaseName } });
    }

    switchToDatabase(databaseName) {
        return http.post('databases/switchTo', {}, { params: { databaseName } });
    }
}

export default new DatabasesService();