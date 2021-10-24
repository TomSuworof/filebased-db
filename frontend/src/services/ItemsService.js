import http from '../http-common'
import getDatabaseName from "../get_current_database";

class ItemsService {
    getAllItems() {
        return http.get('items', { params: { 'databaseName': getDatabaseName() } });
    }

    addItem(item) {
        return http.post('items/add', item, { params: { 'databaseName': getDatabaseName() } });
    }

    editItem(oldItemId, item) {
        return http.put('items/edit', item, { params: { 'databaseName': getDatabaseName(), oldItemId } });
    }

    deleteItem(item) {
        return http.delete('items/delete', { params: { id: item.id, 'databaseName': getDatabaseName() } });
    }

    findItems(searchQuery) {
        return http.get('items/search', { params: { searchQuery, 'databaseName': getDatabaseName() } });
    }

    deleteItems(searchQuery) {
        return http.delete('items/deleteItems', { params: { searchQuery, 'databaseName': getDatabaseName() } });
    }

    clear() {
        return http.delete('items/clear', { params: { 'databaseName': getDatabaseName() } });
    }
}

export default new ItemsService();