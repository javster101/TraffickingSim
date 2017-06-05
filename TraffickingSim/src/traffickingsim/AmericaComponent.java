/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffickingsim;

import com.opengg.core.engine.Resource;
import com.opengg.core.gui.GUI;
import com.opengg.core.gui.GUIText;
import com.opengg.core.math.Vector2f;
import com.opengg.core.math.Vector3f;
import com.opengg.core.render.objects.ObjectCreator;
import com.opengg.core.render.texture.Texture;
import com.opengg.core.world.components.RenderComponent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Javier
 */
public class AmericaComponent extends RenderComponent{
    Texture freedomImage;
    HashMap<String, State> states;
    static President president;
    static Congress congress;
    static int population = 323148587;
    NumberFormat libertyformat = new DecimalFormat("#0.00");
    NumberFormat justiceformat = new DecimalFormat("###,###,###,###,###");
    static List<FirearmRequest> requests = new ArrayList<>();
    float lastTick = 0;   
    int tickCount;
    boolean running = true;
    
    public AmericaComponent(){
        super();
        this.setShader("texture");
        freedomImage = Texture.get(Resource.getTexturePath("map.png"));
        this.setDrawable(ObjectCreator.createQuadPrism(new Vector3f(-1,0,-1), new Vector3f(1,1,1)));
        states = new HashMap<>();
        president = new President("Obama", 0.7f, false);
        congress = new Congress(0.55f, 0.2f);
        setStates();
    }
    
    @Override
    public void update(float delta){
        if(running){
            lastTick += delta;
            if(lastTick < 2)
                return;
            lastTick = 0;
            for(State s : states.values())
                s.update();
            processTrafficking();
            requests.clear();

            ((GUIText)GUI.root.getItem("totviolence")).setText("Violence: " + libertyformat.format(states.values().stream().mapToDouble((s) -> {
                return s.violence;
            }).average().getAsDouble()) + "armed murders per 100,000");
            
            ((GUIText)GUI.root.getItem("totowner")).setText("Gun owners: " + justiceformat.format((long)states.values().stream().mapToDouble((s) -> {
                return s.armed;
            }).sum()) + " owners");
            
            ((GUIText)GUI.root.getItem("totarms")).setText("Firearms in circulation: " + justiceformat.format((long)states.values().stream().mapToDouble((s) -> {
                return s.guns;
            }).sum()) + " firearms");
            
            ((GUIText)GUI.root.getItem("totsurplus")).setText("Surplus firearms: " + justiceformat.format((long)states.values().stream().mapToDouble((s) -> {
                return s.surplus;
            }).sum()) + " firearms");
            
            ((GUIText)GUI.root.getItem("totdesire")).setText("Monthly Demand: " + justiceformat.format((long)states.values().stream().mapToDouble((s) -> {
                return s.desire;
            }).sum()) + " firearms");
            
            ((GUIText)GUI.root.getItem("totlegal")).setText("Montly Income: " + justiceformat.format((long)states.values().stream().mapToDouble((s) -> {
                return s.legalinflux;
            }).sum()) + " firearms");
        }
    }
    
    @Override
    public void render(){
        freedomImage.useTexture(0);
        super.render();
    }
    
    void setStates(){
        states.put("Alabama", new State(new Vector2f(32,-86), "Alabama", 4863300, 0.1f, 0.56f, 2.8f, 0.51f, 0.47f));
        states.put("Alaska", new State(new Vector2f(61,-152), "Alaska", 741894, 0.1f, 0.80f, 2.7f, 0.57f, 0.43f));
        states.put("Arizona", new State(new Vector2f(33,-111), "Arizona", 6931071, 0.2f, 0.47f, 3.6f, 0.31f, 0.40f));
        states.put("Arkansas", new State(new Vector2f(34,-92), "Arkansas", 2988248, 0.2f, 0.59f, 3.2f, 0.55f, 0.43f));
        states.put("California", new State(new Vector2f(36,-119), "California", 39250017, 1, 0.18f, 3.4f, 0.21f, 0.32f));
        states.put("Colorado", new State(new Vector2f(39,-105), "Colorado", 5540545, 0.5f, 0.41f, 1.3f, 0.34f, 0.41f));
        states.put("Connecticut", new State(new Vector2f(41,-72), "Connecticut", 3576452, 0.9f, 0.25f, 2.7f, 0.16f, 0.35f));
        states.put("Delaware", new State(new Vector2f(39,-75), "Delaware", 952065, 0.7f, 0.5f, 4.2f, 0.25f, 0.34f));
        states.put("DC", new State(new Vector2f(38,-77), "DC", 681170, 0.4f, 0.31f, 16.5f, 0.03f, -0.5f));
        states.put("Florida", new State(new Vector2f(27,-81), "Florida", 20612439, 0.2f, 0.36f, 3.9f, 0.24f, 0.39f));
        states.put("Georgia", new State(new Vector2f(33,-83), "Georgia", 10310371, 0.2f, 0.38f, 3.8f, 0.40f, 0.42f));
        states.put("Hawaii", new State(new Vector2f(21,-157), "Hawaii", 1428557, 0.9f, 0.27f, 0.5f, 0.06f, 0.34f));
        states.put("Idaho", new State(new Vector2f(44,-114), "Idaho", 1683140, 0.2f, 0.72f, 0.8f, 0.55f, 0.51f));
        states.put("Illinois", new State(new Vector2f(40,-88), "Illinois", 12801539, 0.8f, 0.31f, 2.8f, 0.20f, 0.34f));
        states.put("Indiana", new State(new Vector2f(39,-86), "Indiana", 6633053, 0.3f, 0.48f, 2.2f, 0.39f, 0.41f));
        states.put("Iowa", new State(new Vector2f(42,-93), "Iowa", 3134693, 0.5f, 0.42f, 0.7f, 0.42f, 0.42f));
        states.put("Kansas", new State(new Vector2f(38,-96), "Kansas", 2907289, 0.1f, 0.59f, 2.2f, 0.42f, 0.47f));
        states.put("Kentucky", new State(new Vector2f(37,-84), "Kentucky", 4436974, 0.2f, 0.60f, 2.7f, 0.47f, 0.44f));
        states.put("Louisiana", new State(new Vector2f(31,-92), "Louisiana", 4681666, 0.2f, 0.47f, 7.7f, 0.44f, 0.42f));
        states.put("Maine", new State(new Vector2f(44,-69), "Maine", 1331479, 0.2f, 0.36f, 0.8f, 0.40f, 0.38f));
        states.put("Maryland", new State(new Vector2f(39,-76), "Maryland", 6016447, 0.9f, 0.20f, 5.1f, 0.21f, 0.31f));
        states.put("Massachusets", new State(new Vector2f(42,-71), "Massachusets", 6811779, 0.9f, 0.24f, 1.8f, 0.12f, 0.29f));
        states.put("Michigan", new State(new Vector2f(43,-84), "Michigan", 9928301, 0.5f, 0.22f, 4.2f, 0.38f, 0.37f));
        states.put("Minnesota", new State(new Vector2f(45,-93), "Minnesota", 5519952, 0.6f, 0.44f, 1, 0.41f, 0.39f));
        states.put("Mississippi", new State(new Vector2f(32,-89), "Mississippi", 2988726, 0.2f, 0.49f, 4, 0.55f, 0.46f));
        states.put("Missouri", new State(new Vector2f(38,-92), "Missouri", 6093000, 0.1f, 0.50f, 5.4f, 0.41f, 0.43f));
        states.put("Montana", new State(new Vector2f(46,-110), "Montana", 1042520, 0.1f, 0.78f, 1.2f, 0.57f, 0.50f));
        states.put("Nebraska", new State(new Vector2f(41,-98), "Nebraska", 1907116, 0.3f, 0.48f, 1.8f, 0.38f, 0.48f));
        states.put("Nevada", new State(new Vector2f(38,-117), "Nevada", 2940058, 0.4f, 0.49f, 3.1f, 0.33f, 0.39f));
        states.put("New Hampshire", new State(new Vector2f(43,-71), "New Hampshire", 1334795, 0.3f, 0.53f, 0.4f, 0.30f, 0.43f));
        states.put("New Jersey", new State(new Vector2f(40,-74), "New Jersey", 8944469, 0.9f, 0.16f, 2.8f, 0.12f, 0.34f));
        states.put("New Mexico", new State(new Vector2f(34,-106), "New Mexico", 2081015, 0.1f, 0.42f, 3.3f, 0.34f, 0.38f));
        states.put("New York", new State(new Vector2f(42,-74), "New York", 19745289, 0.9f, 0.15f, 2.7f, 0.18f, 0.31f));
        states.put("North Carolina", new State(new Vector2f(35,-79), "North Carolina", 10146788, 0.3f, 0.37f, 3, 0.41f, 0.40f));
        states.put("North Dakota", new State(new Vector2f(47,-99), "North Dakota", 757952, 0.1f, 0.58f, 0.6f, 0.50f, 0.46f));
        states.put("Ohio", new State(new Vector2f(40,-82), "Ohio", 11614373, 0.4f, 0.39f, 2.7f, 0.32f, 0.42f));
        states.put("Oklahoma", new State(new Vector2f(35,-96), "Oklahoma", 3923561, 0.1f, 0.55f, 3, 0.42f, 0.45f));
        states.put("Oregon", new State(new Vector2f(44,-112), "Oregon", 4093465, 0.6f, 0.40f, 0.9f, 0.39f, 0.39f));
        states.put("Pennsylvania", new State(new Vector2f(40,-77), "Pennsylvania", 12802503, 0.6f, 0.39f, 3.6f, 0.34f, 0.39f));
        states.put("Rhode Island", new State(new Vector2f(41,-71), "Rhode Island", 1056426, 0.8f, 0.08f, 1.5f, 0.12f, 0.29f));
        states.put("South Carolina", new State(new Vector2f(33,-80), "South Carolina", 4896146, 0.1f, 0.50f, 4.5f, 0.44f, 1f));
        states.put("South Dakota", new State(new Vector2f(44,-99), "South Dakota", 833354, 0.1f, 0.73f, 1, 0.56f, 0.51f));
        states.put("Tennessee", new State(new Vector2f(35,-86), "Tennessee", 6651194, 0.1f, 0.48f, 3.5f, 0.43f, 0.46f));
        states.put("Texas", new State(new Vector2f(31,-97), "Texas", 27862596, 0.1f, 0.44f, 3.2f, 0.35f, 0.41f));
        states.put("Utah", new State(new Vector2f(40,-111), "Utah", 3051217, 0.1f, 0.50f, 0.8f, 0.43f, 0.59f));
        states.put("Vermont", new State(new Vector2f(44,-72), "Vermont", 624594, 0.1f, 0.48f, 0.3f, 0.42f, 0.30f));
        states.put("Virginia", new State(new Vector2f(37,-78), "Virginia", 8411808, 0.4f, 0.38f, 3.1f, 0.35f, 0.41f));
        states.put("Washington", new State(new Vector2f(47,-121), "Washington", 7288000, 0.8f, 0.29f, 1.4f, 0.33f, 0.37f));
        states.put("West Virginia", new State(new Vector2f(38,-80), "West Virginia", 1831102, 0.1f, 0.50f, 1.4f, 0.55f, 0.39f));
        states.put("Wisconsin", new State(new Vector2f(44,-89), "Wisconsin", 5778708, 0.4f, 0.35f, 1.7f, 0.44f, 0.41f));
        states.put("Wyoming", new State(new Vector2f(42,-107), "Wyoming", 585501, 0.1f, 0.80f, 0.9f, 0.59f, 0.58f));
    }
    
    public static void request(String buyer, int num){
        FirearmRequest request = new FirearmRequest();
        request.buyer = buyer;
        request.quantity = -num;
        requests.add(request);
    }
    
    public void processTrafficking(){
        primary: for(State surplus : states.values()){
            if(surplus.surplus <= 0)
                continue;
            System.out.println("");
            System.out.println(surplus.name + " has " + surplus.surplus + ", is willing to sell " + surplus.surplus * (1f-surplus.gunlaws) + " to: ");
            Object[] array = requests.stream().sorted((c1,c2) -> {
                float distance1 = states.get(c1.buyer).position.getDistance(surplus.position);
                float distance2 = states.get(c2.buyer).position.getDistance(surplus.position);
                if(distance1 < distance2 )
                    return 1;
                if(distance1 == distance2)
                    return 0;
                return -1;
            }).toArray();
            
            FirearmRequest[] buyers = new FirearmRequest[array.length];
            
            for(int i = 0; i < buyers.length; i++){
                buyers[i] = (FirearmRequest)array[i];
            }
            float available = surplus.surplus * (1f-surplus.gunlaws);
            float removed = 0;
            
            center: for(FirearmRequest request : buyers){
                if(request.complete){
                    continue center;
                }
                State state = states.get(request.buyer);
                if(available - removed <= request.quantity){
                    float temp = available - removed;
                    state.addFirearms((int) temp);
                    System.out.println("Finished Selling " + temp + " to " + state.name);
                    request.quantity -= temp;
                    surplus.surplus -= temp;
                    continue primary;
                }
                System.out.println("Selling " + request.quantity + " to " + state.name);
                state.addFirearms(request.quantity);
                removed += request.quantity;
                request.complete = true;
                surplus.surplus -= request.quantity;
            }
        }
    }
}
