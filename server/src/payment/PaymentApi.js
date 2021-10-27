const express = require('express');
const router = express.Router();
const service = require('./PaymentService');

router.get('/', (request, response) => {
    service.findAll().then(result => response.json(result));
})

router.get('/:id', (request, response) => {
    service.findById(request.params.id).then(result => response.json(result));
})

router.get('/purchase/:purchase_id', (request, response) => {
    service.findByPurchaseId(request.params.purchase_id).then(result => response.json(result));
})

router.post('/', (request, response) => {
    service.insert(request.body).then(result => response.json(result));
})

router.put('/:id', (request, response) => {
    service.update(request.body).then(result => response.json(result));
})

router.delete('/:id', (request, response) => {
    service.deleteById(request.params.id).then(result => response.json(result));
})

router.post('/:id/confirm', (request, response) => {
    service.confirmPaymentById(request.params.id).then(result => response.json(result));
})

module.exports = router;
