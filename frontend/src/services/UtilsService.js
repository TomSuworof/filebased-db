import http from '../http-common';
import getDatabaseName from '../get_current_database';

class UtilsService {
    getBackupFile() {
        return http.post("utils/backup", null,
            {
                params: { 'databaseName': getDatabaseName() },
                headers:
                    {
                        'Content-Disposition': 'attachment;',
                        'Content-Type': 'text/csv'
                    },
                responseType: 'arraybuffer',
            });
    }

    getBackupFileXLSX() {
        return http.post("utils/backupXLSX", null,
            {
                params: { 'databaseName': getDatabaseName() },
                headers:
                    {
                        'Content-Disposition': 'attachment;',
                        'Content-Type': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    },
                responseType: 'arraybuffer',
            });
    }

    uploadBackupFile(formData) {
        return http.post('utils/uploadBackupFile', formData,
            {
                params: { 'databaseName': getDatabaseName() },
                headers: { 'Content-Type': 'multipart/form-data' }
            });
    }
}

export default new UtilsService();