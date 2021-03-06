package storagemanage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Storagemanage_table")
public class Storagemanage {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String orderId;
    private String custel;
    private String startdate;
    private String state; //호출,호출중,호출확정,호출취소
    private Integer status;
    
    private String storageid;
    private String address;
    private String floor;
    
    @PrePersist
    public void onPrePersist(){
    	System.out.println("###############################=================================");

//    	StoragemanageAssigned StoragemanageAssigned = new StoragemanageAssigned();
//        BeanUtils.copyProperties(this, StoragemanageAssigned);
//        StoragemanageAssigned.publishAfterCommit();
        System.out.println("휴대폰번호 " + custel);
        System.out.println("startdate " + startdate);
        System.out.println("state " + state);
        System.out.println("status " + status);
    	
        System.out.println("orderId " + orderId);
        System.out.println("id " + getId());
        //System.out.println("startdate " + startdate);
        //System.out.println("state " + state);
        //System.out.println("status " + status);
    	
        
        if("호출취소".equals(state)){
			StoragemanageCancelled 택시할당취소됨 = new StoragemanageCancelled();
            BeanUtils.copyProperties(this, 택시할당취소됨);
            택시할당취소됨.publish();

        }else{
//            결제승인됨 결제승인됨 = new 결제승인됨();
//            BeanUtils.copyProperties(this, 결제승인됨);
//
//            //바로 이벤트를 보내버리면 주문정보가 커밋되기도 전에 배송발송됨 이벤트가 발송되어 주문테이블의 상태가 바뀌지 않을 수 있다.
//            // TX 리스너는 커밋이 완료된 후에 이벤트를 발생하도록 만들어준다.
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                @Override
//                public void beforeCommit(boolean readOnly) {
//                    결제승인됨.publish();
//                }
//            });
        	
//        	storageid = "";
//            address = "";
//            floor = "";
//            orderId = "1";
//            custel = "";
//            startdate = "";
//            state = ""; //호출,호출중,호출확정,호출취소
//            status = 0;
            
        	state = "호출중";
        	StoragemanageAssigned 택시할당요청됨 = new StoragemanageAssigned();
        	택시할당요청됨.setId(Long.valueOf(orderId));
        	
        	택시할당요청됨.set고객위치(startdate);
        	택시할당요청됨.setcustel(custel);
        	택시할당요청됨.set예상요금(status);
        	택시할당요청됨.set호출상태(state);
            BeanUtils.copyProperties(this, 택시할당요청됨);
            택시할당요청됨.publishAfterCommit();
            
            
            // 테스트 코드~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//            try {
//                Thread.currentThread().sleep((long) (400 + Math.random() * 220));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }    
    }
    
    
//    @PostPersist
//    public void onPostPersist(){
//    	System.out.println("###############################=================================");
//
////    	StoragemanageAssigned StoragemanageAssigned = new StoragemanageAssigned();
////        BeanUtils.copyProperties(this, StoragemanageAssigned);
////        StoragemanageAssigned.publishAfterCommit();
//        System.out.println("휴대폰번호 " + custel);
//        System.out.println("startdate " + startdate);
//        System.out.println("state " + state);
//        System.out.println("status " + status);
//    	
//        System.out.println("orderId " + orderId);
//        System.out.println("id " + getId());
//        //System.out.println("startdate " + startdate);
//        //System.out.println("state " + state);
//        //System.out.println("status " + status);
//    	
//        
//        if("호출취소".equals(state)){
////            결제취소됨 결제취소됨 = new 결제취소됨();
////            BeanUtils.copyProperties(this, 결제취소됨);
////            결제취소됨.publish();
////        	StoragemanageCancelled StoragemanageCancelled = new StoragemanageCancelled();
////            BeanUtils.copyProperties(this, StoragemanageCancelled);
////            StoragemanageCancelled.publish();
//
//        }else{
////            결제승인됨 결제승인됨 = new 결제승인됨();
////            BeanUtils.copyProperties(this, 결제승인됨);
////
////            //바로 이벤트를 보내버리면 주문정보가 커밋되기도 전에 배송발송됨 이벤트가 발송되어 주문테이블의 상태가 바뀌지 않을 수 있다.
////            // TX 리스너는 커밋이 완료된 후에 이벤트를 발생하도록 만들어준다.
////            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
////                @Override
////                public void beforeCommit(boolean readOnly) {
////                    결제승인됨.publish();
////                }
////            });
//        	
////        	storageid = "";
////            address = "";
////            floor = "";
////            orderId = "1";
////            custel = "";
////            startdate = "";
////            state = ""; //호출,호출중,호출확정,호출취소
////            status = 0;
//            
//        	state = "호출중";
//        	StoragemanageAssigned StoragemanageAssigned = new StoragemanageAssigned();
//        	StoragemanageAssigned.setId(Long.valueOf(orderId));
//        	
//        	StoragemanageAssigned.set고객위치(startdate);
//        	StoragemanageAssigned.setcustel(custel);
//        	StoragemanageAssigned.setStatus(status);
//        	StoragemanageAssigned.setState(state);
//            BeanUtils.copyProperties(this, StoragemanageAssigned);
//            StoragemanageAssigned.publishAfterCommit();
//            
//            
//            // 테스트 코드~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
////            try {
////                Thread.currentThread().sleep((long) (400 + Math.random() * 220));
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }     
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getCustel() {
		return custel;
	}


	public void set휴대폰번호(String 휴대폰번호) {
		this.custel = 휴대폰번호;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate( String startdate ) {
		this.startdate = startdate;
	}
	
	public String getState() {
		return state;
	}
	public void setState( String state ) {
		this.state = state;
	}

	public Integer getStatus() {
		return status;
	}


	public void setStatus( Integer status ) {
		this.status = status;
	}


	public String getStorageid() {
		return storageid;
	}


	public void setStorageid( String storageid ) {
		this.storageid = storageid;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress( String address ) {
		this.address = address;
	}


	public String getFloor() {
		return floor;
	}


	public void setFloor( String floor ) {
		this.floor = floor;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}




}
