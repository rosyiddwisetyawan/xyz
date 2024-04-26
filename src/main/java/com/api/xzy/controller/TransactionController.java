package com.api.xzy.controller;

import com.api.xzy.model.TblTransaction;
import com.api.xzy.model.TblUser;
import com.api.xzy.model.TblVirtualaccount;
import com.api.xzy.model.TransactionData;
import com.api.xzy.model.TransactionRequest;
import com.api.xzy.model.VaRequest;
import com.api.xzy.service.TransactionService;
import com.api.xzy.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService service;

    @Autowired
    private UserInfoService userService;

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    private ResponseEntity<?> checkout(@RequestBody TransactionRequest transactionRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        TblUser tblUser = userService.findByUsername(user.getUsername());
        TblTransaction transaction = service.save(transactionRequest, tblUser);
        Map<String, Object> map = new HashMap<String, Object>();
        if (transaction != null) {
            map.put("message", "checkout success");
            map.put("transaction id", transaction.getId());
            map.put("product", transaction.getProduct());
            map.put("price", transaction.getPrice());
            if(transaction.getPromo()!=null){
                map.put("promo", transaction.getPromo());
            }

        }else{
            map.put("message", "checkout failed");
        }
        return ResponseEntity.ok(map);
    }

    @RequestMapping(value = "/va", method = RequestMethod.POST)
    private ResponseEntity<?> generateVa(@RequestBody VaRequest vaRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        TblUser tblUser = userService.findByUsername(user.getUsername());
        TblTransaction transaction = service.findById(vaRequest.getTrxId());
        TblVirtualaccount tblVirtualaccount = service.addTransaction(transaction, tblUser);
        Map<String, Object> map = new HashMap<String, Object>();
        if (tblVirtualaccount != null) {
            map.put("message", "generate va success");
            map.put("virtualaccount", tblVirtualaccount.getVirtualaccount());

        }else{
            map.put("message", "generate va failed");
        }

        return ResponseEntity.ok(map);
    }

    @RequestMapping(value = "/va/{va}", method = RequestMethod.GET)
    private ResponseEntity<?> generateVa(@PathVariable String va){

        List<TblVirtualaccount> listTblVirtualAccount = service.getList(va);
        List<TransactionData> listTransaction = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        for(TblVirtualaccount tblVirtualaccount : listTblVirtualAccount){
            TblTransaction  tblTransaction = service.findById(tblVirtualaccount.getTransactionId());
            TransactionData data = new TransactionData();
            data.setTransaction(tblTransaction);
            data.setStatusVa(tblVirtualaccount.getStatus());
            data.setVirtualAccount(tblVirtualaccount.getVirtualaccount());
            listTransaction.add(data);
        }

        if(listTransaction.size()==0){
            map.put("message","va not exist");
        }else{
            map.put("message", "success get data");
            map.put("data", listTransaction);
        }

        return ResponseEntity.ok(map);
    }

}
