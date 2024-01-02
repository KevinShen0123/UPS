package amazon_ups;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Delivery {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int delivery_id;
    @Id
    public int package_id;
    public int truck_id;
    public int dest_x;
    public int dets_y;
    public String DESCR;
    public String  D_STATUS ;
    @OneToOne
    @JoinColumn(name = "order_id")
    public Orders order;
}
