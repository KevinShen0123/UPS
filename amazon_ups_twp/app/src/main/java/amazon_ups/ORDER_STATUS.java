package amazon_ups;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.sql.Timestamp;
@Entity
public class ORDER_STATUS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Status_id;
    private String od_status;
    private Timestamp m_time;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
