package com.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.MyOrder;
import com.test.service.MyOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/MyOrder")
@Api(tags = "MyOrder", description = "Operations for My Orders")
public final class MyOrderController 
{		
	private final Logger log = Logger.getLogger(MyOrderController.class);
	
	@Autowired
	private MyOrderService myOrderService;

    // API
    
    @ApiOperation(value = "Add an order", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> addMyOrder(@ApiParam(value = "MyOrder object to be added") @Valid @RequestBody MyOrder body)
    {    	
    	int id = myOrderService.insertMyOrder(body);
    	
    	if(id <= 0)
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
    	log.info("MyOrder id:" + body.getId() + " added.");
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ApiOperation(value = "Get all the orders", response = MyOrder.class)
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getMyOrders()
    {    	
    	List<MyOrder> list_myOrder = myOrderService.getMyOrders();    	
    	return new ResponseEntity<>(list_myOrder, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Get an order", response = MyOrder.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> getMyOrder(@ApiParam(value = "ID of MyOrder") @Valid @PathVariable int id)
    {    	
    	MyOrder myOrder = myOrderService.getMyOrder(id);
    	if(myOrder == null)
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Cancel an order", response = ResponseEntity.class)
    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> cancelMyOrder(@ApiParam(value = "ID of MyOrder") @Valid @RequestBody int id)
    {   
    	log.info("Start cancel id:" + id);
    	try
    	{
	    	int res = myOrderService.cancelMyOrder(id);
	    	log.info("Cancel res: " + res);
	    	
	    	log.info("MyOrder id:" + id + " cancelled.");
	    	return new ResponseEntity<>(HttpStatus.OK);
	    }
		catch(Exception e)
		{
			log.info("FAILED TO CANCEL, MyOrder id:" + id);
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
}
