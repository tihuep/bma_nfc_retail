const express = require('express');
const router = express.Router();
const service = require('./PaymentMethodService');

router.get('/', (request, response) => {
    service.findAll().then(result => response.json(result));
});

router.get('/:id', (request, response) => {
    service.findById(request.params.id).then(result => response.json(result));
});

module.exports = router;
