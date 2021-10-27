const {query} = require("../DatabaseConnector");
const {uuid} = require("uuidv4")
const articlePurchaseService = require("../article_purchase/ArticlePurchaseService")

function findAll() {
    return query('SELECT * FROM purchase');
}

function findAllByUserId(user_id) {
    return query('SELECT * FROM purchase WHERE user_id = ?', user_id)
}

function findById(id) {
    return query('SELECT * FROM purchase WHERE id = ?', id).then(result => {
        const purchase = result[0];
        return articlePurchaseService.findAllByPurchaseId(purchase.id).then(articlePurchases => {
            purchase.items = articlePurchases;
            return purchase;
        });
    });
}

function insert(purchase) {
    query('INSERT INTO purchase (id, user_id) VALUES (?, ?)', purchase.id ? purchase.id : uuid(), purchase.user_id);
    return findById(purchase.id);
}

function update(purchase) {
    query('UPDATE purchase SET user_id = ? WHERE id = ?', purchase.user_id, purchase.id);
    return findById(purchase.id);
}

function deleteById(id) {
    const purchase = findById(id);
    query('DELETE FROM purchase WHERE id = ?', id);
    return purchase;
}

module.exports = {
    findAll,
    findAllByUserId,
    findById,
    insert,
    update,
    deleteById
};
