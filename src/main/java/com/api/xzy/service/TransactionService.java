package com.api.xzy.service;

import com.api.xzy.model.TblTransaction;
import com.api.xzy.model.TblUser;
import com.api.xzy.model.TblVirtualaccount;
import com.api.xzy.model.TransactionRequest;
import com.api.xzy.model.VaRequest;
import com.api.xzy.repository.TblTransactionRepository;
import com.api.xzy.repository.TblVirtualaccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Value("${va.identity}")
    private String vaIdentity;

    @Autowired
    TblTransactionRepository repository;

    @Autowired
    TblVirtualaccountRepository vaRepository;

    public TblTransaction save(TransactionRequest transactionRequest, TblUser user) {
        TblTransaction tblTransaction = new TblTransaction();
        tblTransaction.setPrice(transactionRequest.getPrice());
        tblTransaction.setProduct(transactionRequest.getProduct());
        tblTransaction.setPromo(transactionRequest.getPromo());
        tblTransaction.setUserId(user.getId());
        tblTransaction.setCreateddate(new Timestamp(System.currentTimeMillis()));

        return repository.save(tblTransaction);
    }

    public TblTransaction findById(int trxId){
       Optional<TblTransaction> optionalTblTransaction = repository.findById(trxId);
        if(optionalTblTransaction.isPresent()) {
            TblTransaction tblTransaction = optionalTblTransaction.get();
            return tblTransaction;
        }
       return null;
    }

    public TblVirtualaccount addTransaction(TblTransaction transaction, TblUser user){
        TblVirtualaccount tblVirtualaccount = new TblVirtualaccount();
        tblVirtualaccount.setVirtualaccount(generateVA(user.getMobilenumber()));
        tblVirtualaccount.setTransactionId(transaction.getId());
        tblVirtualaccount.setCreateddate(new Timestamp(System.currentTimeMillis()));

        return vaRepository.save(tblVirtualaccount);
    }
    public String generateVA(String mobileNumber){
        StringBuilder sb = new StringBuilder(vaIdentity).append(mobileNumber);
        return sb.toString();
    }

    public List<TblVirtualaccount> getList(String virtualAccount){
        List<TblVirtualaccount> list = vaRepository.findByVirtualaccount(virtualAccount);
        return list;
    }

}
