const http = require('http');
const fs = require('fs');
const path = require('path');
const root = process.argv[2];
const port = Number(process.argv[3] || 8776);
const server = http.createServer((req, res) => {
  let reqPath = decodeURIComponent(req.url.split('?')[0]);
  if (reqPath === '/') reqPath = '/analysis/langgraph-business-understanding.html';
  const filePath = path.join(root, reqPath.replace(/^\//, ''));
  fs.readFile(filePath, (err, data) => {
    if (err) { res.statusCode = 404; res.end('not found'); return; }
    const ext = path.extname(filePath).toLowerCase();
    const type = ext === '.html' ? 'text/html' : ext === '.css' ? 'text/css' : ext === '.md' ? 'text/markdown' : ext === '.json' ? 'application/json' : 'text/plain';
    res.setHeader('Content-Type', type);
    res.end(data);
  });
});
server.listen(port, '127.0.0.1');
