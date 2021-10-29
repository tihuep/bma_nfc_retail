const mysql = require('mysql')
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'bma',
    password: '(Bma2021)'
})

connection.connect();
connection.query('USE nfc_retail;')

function query(queryString, ...queryParams) {
    return new Promise((resolve, reject) => {
        const sql = mysql.format(queryString, queryParams);
        connection.query(sql, (err, result) => {
            if (err) {
                console.error(err);
                return reject(err);
            }
            resolve(result);
        })
    })
}

module.exports = {
    query
}
