function GravaDadosFoto()
{
   event.preventDefault(); // evita refresh da tela
                
   const URL_TO_FETCH = 'RecebeDados';

   var formData = new FormData(document.getElementById("fdados"));
   //formData.append('acao', 'confirmar'); opcional, caso queira inserir outra informação
                
   fetch(URL_TO_FETCH, { method: 'post',body: formData 
   }).then(function (response) {
        return response.text();
   }).then(function (retorno) {
        // result recebe a resposta do módulo dinâmico
                  
        if (retorno.startsWith('Erro')) // problemas ao alterar/gravar
        {
            document.getElementById('resultado').innerHTML = retorno;
            //document.getElementById('erro').style.display = "block";
        } else  // tudo OK, limpar o formulário
        {
            document.getElementById('fdados').reset(); 
            document.getElementById('resultado').innerHTML = retorno;
        }
   }).catch(function (error) {
        console.error(error);
   });
}

function mostraMusica(myform)
{
    event.preventDefault(); //evitar reload da página    
   
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("post","./MostrarDados");
    var formData = new FormData(myform); //para recuperar os parâmetros do form
    const data = new URLSearchParams();

    for (const pair of formData)   //inserindo os parâmetros individualmente
        data.append(pair[0], pair[1]);
    
    httpRequest.send(data); // enviando os parâmetros junto com a chamada do servlet
    httpRequest.onreadystatechange = function () 
    {
        if (httpRequest.readyState === 4 && httpRequest.status === 200)
        {
            document.getElementById("resultado").innerHTML = httpRequest.responseText;
        }
        
    };   
}
