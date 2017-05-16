/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffickingsim;

/**
 *
 * @author Javier
 */
public class President {
    String name;
    float lawstrength;
    boolean republican;
    
    public President(String name, float rights, boolean repub){
        this.name = name;
        this.lawstrength = rights;
        this.republican = repub;
    }
}
