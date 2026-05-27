// compiler.js
const editor = document.querySelector('.editor-textarea');
const lineNumbers = document.querySelector('.line-numbers');
const runBtn = document.querySelector('.run-btn');
const examplesSelect = document.querySelector('.examples-select');
const panels = document.querySelectorAll('.panel-body');
const prettyPanel = panels[1];
const tacPanel = panels[2];
const app = document.querySelector('.app');
const statusBar = document.querySelector('.status-bar');
const statusHeader = statusBar.querySelector('.status-bar-header');

const examples = {
    'Counting loop': `{
    int i ;
    int sum ;
    i = 0 ;
    sum = 0 ;
    while (i < 10) {
        sum = sum + i ;
        i = i + 1 ;
    }
}`,
    'Conditionals': `{
    int x ;
    int y ;
    x = 10 ;
    y = 0 ;
    if (x > 5)
        y = 1 ;
    else
        y = -1 ;
    do {
        x = x - 1 ;
    } while (x > 0) ;
}`,
    'Arrays': `{
    int[5] a ;
    int i ;
    i = 0 ;
    while (i < 5) {
        a[i] = i * 2 ;
        i = i + 1 ;
    }
}`,
    'Nested arrays': `{
    int[3][3] grid ;
    int i ;
    int j ;
    i = 0 ;
    while (i < 3) {
        j = 0 ;
        while (j < 3) {
            grid[i][j] = i + j ;
            j = j + 1 ;
        }
        i = i + 1 ;
    }
}`,
};

function updateLineNumbers() {
    const lines = editor.value.split('\n').length;
    let html = '';
    for (let i = 1; i <= lines; i++) html += `<div>${i}</div>`;
    lineNumbers.innerHTML = html;
}

function showCode(panel, text, highlighter) {
    panel.innerHTML = '';
    const pre = document.createElement('div');
    pre.className = 'code-output';
    pre.innerHTML = highlighter(text);
    panel.appendChild(pre);
}

function resetPanels() {
    for (const panel of [prettyPanel, tacPanel])
        panel.innerHTML = '<div class="empty-state">Run the compiler to see output.</div>';
}


function clearErrors() {
    app.classList.remove('has-errors');
}

function showError(errorHtml) {
    clearErrors();
    app.classList.add('has-errors');
    const item = document.createElement('div');
    item.className = 'error-item';
    item.innerHTML = errorHtml;
}

function compileCode() {
    const source = editor.value;
    let result;
    try {
        const json = compile(source); // compile imported from Java compiled JS script.
        result = JSON.parse(json);
    } catch (e) {
        showError(`Runtime error: ${e.message}`);
        return;
    }

    if (result.success) {
        setStatus('success', 'Compiled', '<span class="status-item-msg">Compiled successfully!</span>');
        showCode(prettyPanel, result.pretty, highlightPretty);
        showCode(tacPanel, result.tac, highlightTac);
    } else {
        resetPanels();
        setStatus('error', 'Errors', result.error);
    }
}

editor.addEventListener('input', updateLineNumbers);
runBtn.addEventListener('click', compileCode);

document.addEventListener('keydown', (e) => {
    if ((e.metaKey || e.ctrlKey) && e.key === 'Enter') {
        e.preventDefault();
        compileCode();
    }
});

examplesSelect.addEventListener('change', (e) => {
    const code = examples[e.target.value];
    if (code) {
        editor.value = code;
        updateLineNumbers();
        e.target.selectedIndex = 0;
    }
});

function setStatus(kind, headerText, bodyHtml) {
    statusBar.classList.remove('is-error', 'is-success', 'is-ready');
    statusBar.classList.add(`is-${kind}`);
    statusHeader.textContent = headerText;

    statusBar.querySelectorAll('.status-item').forEach(el => el.remove());
    const item = document.createElement('div');
    item.className = 'status-item';
    item.innerHTML = bodyHtml;
    statusBar.appendChild(item);
}

function escapeHtml(str) {
    return str
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}

function highlightPretty(code) {
    code = escapeHtml(code);
    const tokenRe = /\b(if|else|while|do|break)\b|\b(int|float|bool|char|true|false)\b|\b(\d+\.\d+|\d+)\b|(\|\||&amp;&amp;|==|!=|&lt;=|&gt;=|&lt;|&gt;|\+|-|\*|\/|!)|([{}\[\];])/g;
    return code.replace(tokenRe, (match, kw, type, lit, op, punct) => {
        if (kw)    return `<span class="tok-kw">${match}</span>`;
        if (type)  return `<span class="tok-kw">${match}</span>`;
        if (lit)   return `<span class="tok-num">${match}</span>`;
        if (op)    return `<span class="">${match}</span>`;
        if (punct) return `<span class="tok-punct">${match}</span>`;
        return match;
    });
}

function highlightTac(code) {
    code = escapeHtml(code);
    code = code.replace(/\bL\d+:/g, m => `<span class="tok-label">${m}</span>`);
    code = code.replace(/\b(iffalse|if|goto|true|false)\b/g, m => `<span class="tok-kw">${m}</span>`);
    code = code.replace(/\bt\d+\b/g, m => `<span class="tok-tmp">${m}</span>`);
    code = code.replace(/\b(\d+(\.\d+)?)\b/g, m => `<span class="tok-num">${m}</span>`);
    return code;
}

updateLineNumbers();