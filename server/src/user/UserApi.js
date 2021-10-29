const express = require('express');
const router = express.Router();
const service = require('./UserService');

router.get('/', (request, response) => {
    service.findAll().then(result => response.json(result));
})

router.get('/:id', (request, response) => {
    service.findById(request.params.id).then(result => response.json(result));
})

router.put('/:id', (request, response) => {
    service.update(request.body).then(result => response.json(result), () => response.sendStatus(500));
})

router.delete('/:id', (request, response) => {
    service.deleteById(request.params.id).then(result => response.json(result), () => response.sendStatus(500));
})

module.exports = router;
