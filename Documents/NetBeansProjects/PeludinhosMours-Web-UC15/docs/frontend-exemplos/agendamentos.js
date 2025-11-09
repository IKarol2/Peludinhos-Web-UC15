// Exemplo de chamada com mensagens de sucesso/erro em PT-BR
async function enviarAgendamento(dto){
  try{
    const resp = await fetch('/api/agendamentos', {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify(dto)
    });
    const data = await resp.json().catch(()=>({}));
    if(resp.ok){
      alert(data.mensagem || 'Agendamento criado com sucesso!');
      return;
    }
    if(data && data.erros){
      const msgs = Object.entries(data.erros).map(([campo,msg])=> `${campo}: ${msg}`).join('\n');
      alert('Não foi possível salvar.\n' + msgs);
    } else {
      alert(data.mensagem || 'Não foi possível salvar. Verifique os dados e tente novamente.');
    }
  }catch(e){
    alert('Erro de conexão. Tente novamente em instantes.');
  }
}
