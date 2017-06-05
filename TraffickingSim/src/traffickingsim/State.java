/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffickingsim;

import static com.opengg.core.math.FastMath.clamp;
import com.opengg.core.math.Vector2f;

/**
 *
 * @author Javier
 */
public class State {
    Vector2f position;
    String name;
    int population;
    float partisan;
    
    float gunlaws;
    float gunmarket;
    float violence;
    float ownership;
    float crimeguns;
    
    float surplus;
    float armed;
    float guns;
    float legalinflux;
    float desire;
    
    public State(Vector2f position, String name, int population, float gunlaws, float sales, float violence, float ownership, float partisan){
        this.position = position;
        this.name = name;
        this.ownership = ownership;
        this.violence = violence;
        this.gunlaws = gunlaws;
        this.gunmarket = sales;
        this.population = population;
        this.partisan = partisan;
        this.armed = ownership * population;
        this.guns = armed * 1.8f;
        this.desire = (float) (Math.pow(((violence / 100000) * population), 0.3f) + 
                (((partisan) * ((population-armed)/population)) * population * gunlaws * 
                (AmericaComponent.president.lawstrength + 0.5 + (AmericaComponent.president.republican ? -0.2 : 0.2)) * gunlaws * 0.6f)) * 0.6f;
        this.legalinflux = sales * (armed * 0.3f);
        this.surplus = legalinflux - desire;
    }
    
    public void update(){
        float newguns = legalinflux;
        float newarmed = newguns / 1.8f;
        armed += newarmed;
        guns += newguns;
        population *= 1.0069;
        desire = (float) (Math.pow(((violence / 100000) * population), 1.1f) + 
                (((partisan) * ((population-armed)/population)) * population * gunlaws * 
                (AmericaComponent.president.lawstrength + 0.5 + (AmericaComponent.president.republican ? -0.2 : 0.2)) * gunlaws * 0.6f)) * 0.3f;
        violence += -(((gunlaws - 0.5f) * (violence/1000)) + ((armed / population) * ((1-gunlaws)-0.5f) * 0.6f)) * 0.25f * (1 + (crimeguns / guns));
        legalinflux += -(surplus * (1.2f - gunlaws) * 0.3f);
        if(legalinflux < 0)
            legalinflux = armed * 0.07f;
        if(legalinflux > population * 0.1f * (1.2f-gunlaws))
            legalinflux = population * 0.1f * (1.2f-gunlaws);
        surplus += legalinflux - desire;
        
        if(surplus < 0){
            float request = surplus * 1.3f - gunlaws;
            if(surplus < request)
                request = surplus;
            AmericaComponent.request(name, (int) request);
        }
    }
    
    public void addFirearms(int num){
        float newguns = num;
        float newarmed = newguns / 1.8f;
        armed += newarmed;
        guns += newguns;
        crimeguns += newguns;
        surplus += num;
    }
}
