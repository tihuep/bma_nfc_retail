const {query} = require("../DatabaseConnector");
const {uuid} = require("uuidv4")

function findAll() {
    return query('SELECT * FROM payment');
}

function findById(id) {
    return query('SELECT * FROM payment WHERE id = ?', id).then(result => result[0]);
}

function findByPurchaseId(purchaseId) {
    return query('SELECT * FROM payment WHERE purchase_id = ?', purchaseId).then(result => result[0]);
}

function insert(payment) {
    const id = payment.id ? payment.id : uuid();
    query('INSERT INTO payment (id, total, currency, confirmed, confirmation_date, purchase_id, payment_method_id) VALUES (?, ?, ?, ?, ?, ?, ?)',
        id, payment.total, payment.currency, payment.confirmed ? payment.confirmed : 'DEFAULT', payment.confirmation_date, payment.purchase_id, payment.payment_method_id);
    return findById(id);
}

function update(payment) {
    query('UPDATE payment SET total = ?, confirmed = ?, confirmation_date = ?, purchase_id = ?, payment_method_id = ? WHERE id = ?',
        payment.total, payment.currency, payment.confirmed, payment.confirmation_date, payment.purchase_id, payment.payment_method_id, payment.id)
    return findById(payment.id);
}

function deleteById(id) {
    const payment = findById(id)
    query('DELETE FROM payment WHERE id = ?', id);
    return payment;
}

module.exports = {
    findAll,
    findById,
    findByPurchaseId,
    insert,
    update,
    deleteById
}
