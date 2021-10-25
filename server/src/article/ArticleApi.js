const router = require('../Router');
const service = require('./ArticleService');

router.get('/', (request, response) => {
    service.findAll().then(result => response.send(result));
});

router.get('/:id', (request, response) => {
    service.findById(request.params.id).then(result => response.send(result));
});

module.exports = router;
