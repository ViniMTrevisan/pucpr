document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            // Redireciona para a página principal diretamente (sem validar)
            window.location.href = '/principal';
        });
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const cadastroForm = document.getElementById('cadastroForm');
    
    if (cadastroForm) {
        cadastroForm.addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Cadastro realizado com sucesso!');
            window.location.href = '/login';
        });
    }
});

function voluntariar(ongNome) {
    alert(`Você se voluntariou para a ONG: ${ongNome}!`);
}

function doar() {
    alert("Obrigado por sua doação! Sua ajuda fará a diferença ❤️");
}
