const express = require('express');
const router = express.Router();
const service = require('./ArticlePurchaseService');

router.get('/:purchase_id/items', (request, response) => {
    service.findAllByPurchaseId(request.params.purchase_id).then(result => response.json(result));
})

router.get('/:purchase_id/items/:article_id', (request, response) => {
    service.findByArticleIdAndPurchaseId(request.params.article_id, request.params.purchase_id).then(result => response.json(result));
})

router.post('/:purchase_id/items', (request, response) => {
    service.insert(request.body).then(result => response.json(result));
})

router.put('/:purchase_id/items', (request, response) => {
    service.update(request.body).then(result => response.json(result));
})

router.delete('/:purchase_id/items/:article_id', (request, response) => {
    service.deleteByArticleIdAndPurchaseId(request.params.article_id, request.params.purchase_id).then(result => response.json(result));
})

module.exports = router;
