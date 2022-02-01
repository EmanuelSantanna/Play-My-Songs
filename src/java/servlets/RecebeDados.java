
package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
    location="/", 
    fileSizeThreshold=1024*1024,    // 1MB *      
    maxFileSize=1024*1024*100,      // 100MB **
    maxRequestSize=1024*1024*10*10  // 100MB *
)

@WebServlet(name = "RecebeDados", urlPatterns = {"/RecebeDados"})
public class RecebeDados extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensagem = "Musica recebida com sucesso!";
        try
        {
           
            String musica=request.getParameter("musica");
            String cantor=request.getParameter("cantor");
            String genero=request.getParameter("Gen");
            
            String musOri = musica;
            String canOri = cantor;
            musica = musica.replace(".","");
            musica = musica.replace(";","");
            musica = musica.replace(",","");
            musica = musica.replace("$","");
            musica = musica.replace("#","");
            musica = musica.replace("%","");         
            musica = musica.replace("@","");
            musica = musica.replace("*","");
            musica = musica.replaceAll(" ", "");
            
            cantor = cantor.replace("#","");
            cantor = cantor.replace(";","");
            cantor = cantor.replace(",","");
            cantor = cantor.replace("$","");
            cantor = cantor.replace("%","");
            cantor = cantor.replace("@","");   
            cantor = cantor.replace(".",""); 
            cantor = cantor.replace("*",""); 
            cantor = cantor.replaceAll(" ", "");
            
            if(musica.length()<2 || cantor.length()<2)
                throw new Exception(mensagem = "Musica ou Cantor devem ter mais que 2 LETRAS!");
            else
            {
                // recebe a foto
                Part foto = request.getPart("foto");
                // cria um vetor para armazenar a foto
                byte[] imagem=new byte[(int)foto.getSize()];
                // carrega o vetor com os bytes da foto
                foto.getInputStream().read(imagem);
                // cria um arquivo com o mesmo nome da foto e grava o vetor como seu conteúdo
                FileOutputStream arquivo = new FileOutputStream(new File(getServletContext().getRealPath("/") + "/" + musica +"_"+ genero+"_"+cantor+".mp3"));
                arquivo.write(imagem);
                arquivo.close();
                String nomearq = musOri+"#"+musica +"_"+ genero+"_"+cantor+".mp3"+"#"+canOri+"\n";
                RandomAccessFile raf;
                raf = new RandomAccessFile(request.getServletContext().getRealPath("")+"//ListaMusica.txt","rw");
                raf.seek(raf.length());
                raf.writeBytes(nomearq);
                raf.close();

            }
            
            
        }catch(Exception e){}
        try (PrintWriter out = response.getWriter()) {
            out.print(mensagem);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

}
