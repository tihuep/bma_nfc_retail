const express = require('express');
const router = express.Router();
const service = require('./ArticlePurchaseService');
const authService = require('../authentication/AuthenticationService');

router.get('/:purchase_id/items', authService.authenticateToken, (request, response) => {
    service.findAllByPurchaseId(request.params.purchase_id).then(result => response.json(result), error => response.status(500).send(error));
})

router.get('/:purchase_id/items/:article_id', authService.authenticateToken, (request, response) => {
    service.findByArticleIdAndPurchaseId(request.params.article_id, request.params.purchase_id).then(result => response.json(result), error => response.status(500).send(error));
})

router.post('/:purchase_id/items', authService.authenticateToken, (request, response) => {
    service.insert(request.body).then(result => response.json(result), error => response.status(500).send(error));
})

router.put('/:purchase_id/items/:article_id', authService.authenticateToken, (request, response) => {
    service.update(request.body).then(result => response.json(result), error => response.status(500).send(error));
})

router.delete('/:purchase_id/items/:article_id', authService.authenticateToken, (request, response) => {
    service.deleteByArticleIdAndPurchaseId(request.params.article_id, request.params.purchase_id).then(result => response.json(result), error => response.status(500).send(error));
})

module.exports = router;
