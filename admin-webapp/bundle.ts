const fs = require('fs');
const archiver = require('archiver');

const out = 'dist/application.war';

const output = fs.createWriteStream(out);
const archive = archiver('zip', {});

output.on('finish', () => {
    console.log('war (' + out + ') ' + archive.pointer() + ' total bytes');
});
archive.pipe(output);
archive.bulk([{ expand: true, cwd: 'dist/application', src: ['**'], dest: '/'}]);
archive.finalize();
