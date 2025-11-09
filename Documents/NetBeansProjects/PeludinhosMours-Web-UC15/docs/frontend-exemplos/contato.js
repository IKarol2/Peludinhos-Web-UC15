// Exemplo de chamada com mensagens de sucesso/erro em PT-BR
async function enviarContato(dto){
  try{
    const resp = await fetch('/api/contato', {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify(dto)
    });
    const data = await resp.json().catch(()=>({}));
    if(resp.status === 202){
      alert(data.mensagem || 'Mensagem enviada! Em breve entraremos em contato.');
      return;
    }
    if(data && data.erros){
      const msgs = Object.entries(data.erros).map(([campo,msg])=> `${campo}: ${msg}`).join('\n');
      alert('Não foi possível enviar sua mensagem.\n' + msgs);
    } else {
      alert(data.mensagem || 'Não foi possível enviar sua mensagem. Verifique os dados e tente novamente.');
    }
  }catch(e){
    alert('Erro de conexão. Tente novamente em instantes.');
  }
}
