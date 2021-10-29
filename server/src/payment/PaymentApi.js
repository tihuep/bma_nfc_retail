const express = require('express');
const router = express.Router();
const service = require('./PaymentService');
const authService = require('../authentication/AuthenticationService');

router.get('/', authService.authenticateToken, (request, response) => {
    service.findAll().then(result => response.json(result));
})

router.get('/:id', authService.authenticateToken, (request, response) => {
    service.findById(request.params.id).then(result => response.json(result));
})

router.get('/purchase/:purchase_id', authService.authenticateToken, (request, response) => {
    service.findByPurchaseId(request.params.purchase_id).then(result => response.json(result));
})

router.post('/', authService.authenticateToken, (request, response) => {
    service.insert(request.body).then(result => response.json(result));
})

router.put('/:id', authService.authenticateToken, (request, response) => {
    service.update(request.body).then(result => response.json(result));
})

router.delete('/:id', authService.authenticateToken, (request, response) => {
    service.deleteById(request.params.id).then(result => response.json(result));
})

router.post('/:id/confirm', authService.authenticateToken, (request, response) => {
    service.confirmPaymentById(request.params.id).then(result => response.json(result));
})

module.exports = router;
