const express = require('express');
const router = express.Router();
const service = require('./PurchaseService');

router.get('/', (request, response) => {
    service.findAll().then(result => response.json(result));
})

router.get('/user/:user_id', (request, response) => {
    service.findAllByUserId(request.params.user_id).then(result => response.json(result));
})

router.get('/:id', (request, response) => {
    service.findById(request.params.id).then(result => response.json(result));
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

module.exports = router;
