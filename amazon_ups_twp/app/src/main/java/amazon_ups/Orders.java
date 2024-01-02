package amazon_ups;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Orders{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int order_id;
    public String ups_account;
    public int dest_x;
    public int dest_y;
}
