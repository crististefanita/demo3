const http = require('http');
const fs = require('fs');
const path = require('path');
const packageRoot = path.resolve('docs/out/2026-06-17_12.08.06');
const docsRoot = path.resolve('docs');
const mime = { '.html':'text/html; charset=utf-8', '.md':'text/plain; charset=utf-8', '.json':'application/json; charset=utf-8', '.txt':'text/plain; charset=utf-8', '.png':'image/png', '.diff':'text/plain; charset=utf-8', '.css':'text/css; charset=utf-8', '.properties':'text/plain; charset=utf-8', '.xml':'application/xml; charset=utf-8' };
http.createServer((req,res) => {
  const urlPath = decodeURIComponent((req.url || '/').split('?')[0]);
  let filePath;
  if (urlPath === '/shared-report-reference.css') {
    filePath = path.join(docsRoot, 'shared-report-reference.css');
  } else {
    filePath = path.join(packageRoot, urlPath === '/' ? '/analysis/langgraph-business-understanding.html' : urlPath);
    if (!filePath.startsWith(packageRoot)) { res.statusCode = 403; return res.end('forbidden'); }
  }
  fs.readFile(filePath, (err, data) => {
    if (err) { res.statusCode = 404; return res.end('not found'); }
    res.setHeader('Content-Type', mime[path.extname(filePath)] || 'application/octet-stream');
    res.end(data);
  });
}).listen(8765, '127.0.0.1');
setInterval(() => {}, 1000);
