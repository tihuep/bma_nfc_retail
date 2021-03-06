const jwt = require('jsonwebtoken');

const ACCESS_TOKEN_SECRET = '583a4ff987d7b563e183eb0f057d2b34eaf679417cb7a22854b6be7bae884d59271f978de3e81af6df7a70145ca1aec6057c1f8642b77cdf72182c33e3d87f1b';

function authenticateToken(request, response, next) {
    const authorizationHeader = request.headers.authorization;
    const token = authorizationHeader && authorizationHeader.split(' ')[1];
    if (token == null) return response.sendStatus(401);

    jwt.verify(token, ACCESS_TOKEN_SECRET, (error, user) => {
        if (error) return response.sendStatus(403);
        request.user = user;
        next();
    })
}

module.exports = {
    authenticateToken,
    ACCESS_TOKEN_SECRET
};
