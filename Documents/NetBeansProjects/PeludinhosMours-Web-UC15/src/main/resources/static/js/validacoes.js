function showMsg(el,text,ok=false){el.textContent=text;el.style.color=ok?'#22d3ee':'#ffb4b4';}
function isEmail(v){return/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v||'');}
function isPhone(v){return /^\(?\d{2}\)?[\s-]?\d{4,5}-?\d{4}$/.test(v||'');}
document.addEventListener('DOMContentLoaded',()=>{
 const ag=document.getElementById('form-agendamento');
 if(ag){const out=document.getElementById('msg-form');ag.addEventListener('submit',e=>{
  e.preventDefault();const d=Object.fromEntries(new FormData(ag).entries());
  if(!d.nome.trim() || d.nome.trim().split(/\s+/).length < 2) return showMsg(out,'Digite seu nome completo.');
  if(!isEmail(d.email)) return showMsg(out,'E-mail inválido.');
  if(!isPhone(d.telefone)) return showMsg(out,'Telefone inválido. Use (DDD) 90000-0000.');
  if(!d.pet.trim()) return showMsg(out,'Informe o nome do pet.');
  if(!['PEQUENO','MEDIO','GRANDE'].includes(d.porte)) return showMsg(out,'Selecione o porte.');
  if(!d.servico) return showMsg(out,'Selecione o serviço.');
  if(!d.data) return showMsg(out,'Informe a data.');
  if(!d.hora) return showMsg(out,'Informe o horário.');
  showMsg(out,'Pedido enviado com sucesso!',true); ag.reset();
 });}
 const ct=document.getElementById('form-contato');
 if(ct){const out=document.getElementById('msg-contato');ct.addEventListener('submit',e=>{
  e.preventDefault();const d=Object.fromEntries(new FormData(ct).entries());
  if(!d.nome.trim() || d.nome.trim().split(/\s+/).length < 2) return showMsg(out,'Digite seu nome completo.');
  if(!isEmail(d.email)) return showMsg(out,'E-mail inválido.');
  if(!isPhone(d.telefone)) return showMsg(out,'Telefone inválido. Use (DDD) 90000-0000.');
  if(!d.mensagem.trim()) return showMsg(out,'Escreva sua mensagem.');
  showMsg(out,'Mensagem enviada!',true); ct.reset();
 });}
});

// --- Limitar o ano a 4 dígitos na entrada de data e restringir o intervalo. ---
(function(){
  const inp = document.querySelector('input[name="data"]');
  if(inp){
    try{ inp.setAttribute('min','1900-01-01'); inp.setAttribute('max','2099-12-31'); }catch(e){}
    inp.addEventListener('input', ()=>{
      const v = inp.value || '';
      if(/^\d{5,}-\d{2}-\d{2}$/.test(v)){
        const [y,m,d] = v.split('-');
        inp.value = y.slice(0,4) + '-' + m + '-' + d;
      }
    });
  }
})();
