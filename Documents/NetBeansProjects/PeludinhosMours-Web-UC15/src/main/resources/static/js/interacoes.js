// interacoes.js – Peludinhos Mours (Etapa 9) – versão alinhada ao back-end

(function () {
  // ===== helpers =====
  const $ = (sel, root = document) => root.querySelector(sel);

  function getFormData(form) {
    const data = new FormData(form);
    const obj = {};
    for (const [k, v] of data.entries()) obj[k] = typeof v === "string" ? v.trim() : v;
    return obj;
  }

  async function postJSON(url, data) {
    const res = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    const text = await res.text().catch(() => "");
    let body = null;
    try { body = text ? JSON.parse(text) : null; } catch { body = text; }
    return { ok: res.ok, status: res.status, body };
  }

  function showMsg(el, text, ok = true) {
    if (!el) return;
    el.textContent = text;
    el.classList.remove("msg--error", "msg--success");
    el.classList.add(ok ? "msg--success" : "msg--error");
  }

  // ===== normalizações exigidas pelo back =====
  // Controller aceita dd/MM/yyyy e HH:mm — deixamos garantido
  function normalizaData(valor) {
    if (!valor) return null;
    // aceita "yyyy-MM-dd" do input[type=date] e converte para "dd/MM/yyyy"
    const m = /^(\d{4})-(\d{2})-(\d{2})$/.exec(valor);
    if (m) return `${m[3]}/${m[2]}/${m[1]}`;
    // se já vier dd/MM/yyyy, mantém
    const b = /^(\d{2})\/(\d{2})\/(\d{4})$/.exec(valor);
    if (b) return valor;
    return valor; // última tentativa (será validado no back)
  }

  function normalizaHora(valor) {
    if (!valor) return null;
    // aceita "HH:mm" do input[type=time]
    return valor;
  }

  // ===== Agendamentos =====
  const formAg = $("#form-agendamento");
  if (formAg) {
    const msg = $("#msg-form-agendamento");
    formAg.addEventListener("submit", async (e) => {
      e.preventDefault();

      if (!formAg.checkValidity()) {
        formAg.reportValidity();
        showMsg(msg, "Corrija os campos destacados e tente novamente.", false);
        return;
      }
      const d = getFormData(formAg);
      const payload = {
        nome: d.nome,
        email: d.email,
        telefone: d.telefone,
        pet: d.pet,
        porte: d.porte,            // Pequeno|Médio|Grande
        servico: d.servico,        // Banho|Tosa|...
        data: normalizaData(d.data),
        horario: normalizaHora(d.horario),
        observacoes: d.obs || ""
      };

      showMsg(msg, "Enviando...", true);
      try {
        const { ok, status, body } = await postJSON("/api/agendamentos", payload);
        if (ok) {
          showMsg(msg, "Pedido enviado com sucesso!");
          formAg.reset();
        } else {
          const detalhe = body && (body.mensagem || body.message || body.error || JSON.stringify(body));
          showMsg(msg, `Falha ao enviar (HTTP ${status}). ${detalhe || ""}`, false);
        }
      } catch (err) {
        showMsg(msg, "Erro de rede ao enviar. Verifique se o servidor está em execução.", false);
      }
    });
  }

  // ===== Contato =====
  const formCt = $("#form-contato");
  if (formCt) {
    const msg = $("#msg-form-contato");
    formCt.addEventListener("submit", async (e) => {
      e.preventDefault();

      if (!formCt.checkValidity()) {
        formCt.reportValidity();
        showMsg(msg, "Corrija os campos destacados e tente novamente.", false);
        return;
      }

      const d = getFormData(formCt);
      const payload = {
        nome: d.nome,
        email: d.email,
        telefone: d.telefone,
        mensagem: d.mensagem
      };

      showMsg(msg, "Enviando...", true);
      try {
        const { ok, status, body } = await postJSON("/api/contato", payload);
        if (ok) {
          showMsg(msg, "Mensagem enviada com sucesso!");
          formCt.reset();
        } else {
          const detalhe = body && (body.mensagem || body.message || body.error || JSON.stringify(body));
          showMsg(msg, `Falha ao enviar (HTTP ${status}). ${detalhe || ""}`, false);
        }
      } catch (err) {
        showMsg(msg, "Erro de rede ao enviar. Verifique se o servidor está em execução.", false);
      }
    });
  }
})();


