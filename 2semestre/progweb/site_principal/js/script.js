document.addEventListener('DOMContentLoaded', () => {
    // Form Validation for Registration (ONG and Volunteer)
    const registrationForms = document.querySelectorAll('form[action="cadastro_ong.php"], form[action="cadastro_voluntario.php"]');
    registrationForms.forEach(form => {
        form.addEventListener('submit', (e) => {
            const name = form.querySelector('input[name="name"]').value.trim();
            const email = form.querySelector('input[name="email"]').value.trim();
            const phone = form.querySelector('input[name="phone"]').value.trim();
            const cep = form.querySelector('input[name="cep"]').value.trim();
            const address = form.querySelector('input[name="address"]').value.trim();
            const password = form.querySelector('input[name="password"]').value.trim();

            if (!name || !email || !phone || !cep || !address || !password) {
                alert('Por favor, preencha todos os campos.');
                e.preventDefault();
                return;
            }

            if (!email.includes('@') || !email.includes('.')) {
                alert('Por favor, insira um email válido (exemplo: usuario@dominio.com).');
                e.preventDefault();
            }
        });
    });

    // Form Validation for Login Pages (ONG, Volunteer, Admin)
    const loginForms = document.querySelectorAll('form[action="login_ong.php"], form[action="login_voluntario.php"], form[action="login_admin.php"]');
    loginForms.forEach(form => {
        form.addEventListener('submit', (e) => {
            const email = form.querySelector('input[name="email"]').value.trim();
            const password = form.querySelector('input[name="password"]').value.trim();

            if (!email || !password) {
                alert('Por favor, preencha o email e a senha.');
                e.preventDefault();
                return;
            }

            if (!email.includes('@') || !email.includes('.')) {
                alert('Por favor, insira um email válido (exemplo: usuario@dominio.com).');
                e.preventDefault();
            }
        });
    });

    // Confirmation Prompt for "Entrar em Contato" in analise.html
    const contactButton = document.querySelector('.candidato a[href*="processar_contato.php"]');
    if (contactButton) {
        contactButton.addEventListener('click', (e) => {
            if (!confirm('Tem certeza que deseja entrar em contato com este voluntário?')) {
                e.preventDefault();
            }
        });
    }

    // Confirmation Prompt for Approving/Rejecting ONG in aprovar_ong.html
    const approveRejectForm = document.querySelector('form[action="/aprovar_ong"]');
    if (approveRejectForm) {
        approveRejectForm.addEventListener('submit', (e) => {
            const action = e.submitter.value;
            if (!confirm(`Tem certeza que deseja ${action.toLowerCase()} esta ONG?`)) {
                e.preventDefault();
            }
        });
    }

    // Confirmation Prompt for Removing ONG/Volunteer in aprovacao_admin.php
    const removeButtons = document.querySelectorAll('.remover a.btn');
    removeButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            const type = button.textContent.includes('ONG') ? 'esta ONG' : 'este voluntário';
            if (!confirm(`Tem certeza que deseja remover ${type}?`)) {
                e.preventDefault();
            }
        });
    });

    // Search Filter for ONGs in voluntarios_selecionando_ongs.php
    const ongList = document.querySelector('.ong-list');
    if (ongList) {
        const searchInput = document.createElement('input');
        searchInput.type = 'text';
        searchInput.placeholder = 'Pesquisar ONGs...';
        searchInput.style.margin = '10px';
        searchInput.style.padding = '5px';
        searchInput.style.width = 'calc(100% - 20px)';
        ongList.parentElement.insertBefore(searchInput, ongList);

        searchInput.addEventListener('input', () => {
            const filter = searchInput.value.toLowerCase();
            const ongItems = ongList.querySelectorAll('.ong-item');

            ongItems.forEach(item => {
                const ongName = item.querySelector('h3').textContent.toLowerCase();
                if (ongName.includes(filter)) {
                    item.style.display = 'block';
                } else {
                    item.style.display = 'none';
                }
            });
        });
    }

    // Toggle Publicar/Analisar Sections in publicacao_ong.php
    const publicacaoForm = document.querySelector('form[action="/publicacao_ong"]');
    const candidateList = document.querySelector('.candidate-list');
    if (publicacaoForm && candidateList) {
        const togglePublicar = document.createElement('button');
        togglePublicar.textContent = 'Mostrar Formulário de Publicação';
        togglePublicar.style.margin = '10px';
        togglePublicar.style.padding = '5px 10px';

        const toggleAnalisar = document.createElement('button');
        toggleAnalisar.textContent = 'Mostrar Candidatos';
        toggleAnalisar.style.margin = '10px';
        toggleAnalisar.style.padding = '5px 10px';

        publicacaoForm.parentElement.insertBefore(togglePublicar, publicacaoForm);
        candidateList.parentElement.insertBefore(toggleAnalisar, candidateList);

        publicacaoForm.style.display = 'none';
        candidateList.style.display = 'none';

        togglePublicar.addEventListener('click', () => {
            publicacaoForm.style.display = publicacaoForm.style.display === 'none' ? 'block' : 'none';
            candidateList.style.display = 'none';
        });

        toggleAnalisar.addEventListener('click', () => {
            candidateList.style.display = candidateList.style.display === 'none' ? 'block' : 'none';
            publicacaoForm.style.display = 'none';
        });
    }

    // Toggle Sections in index.html and usuario.html
    const sections = document.querySelectorAll('.about, .tela, .ajudar');
    sections.forEach(section => {
        const title = section.querySelector('h2');
        if (title) {
            title.style.cursor = 'pointer';
            title.addEventListener('click', () => {
                const content = section.querySelector('p, ul');
                if (content) {
                    content.style.display = content.style.display === 'none' ? 'block' : 'none';
                }
            });

            const content = section.querySelector('p, ul');
            if (content) {
                content.style.display = 'none';
            }
        }
    });

    // Confirmation Prompt for Volunteer Application in envio.html
    const volunteerButton = document.querySelector('.envio a.btn');
    if (volunteerButton) {
        volunteerButton.addEventListener('click', (e) => {
            if (!confirm('Tem certeza que deseja se voluntariar para esta ONG?')) {
                e.preventDefault();
            }
        });
    }
});