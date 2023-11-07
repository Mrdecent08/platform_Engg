//package com.example.demo;
//
// 
//import java.io.BufferedReader;
//
//import java.io.File;
//
//import java.io.FileReader;
//
//import java.util.*;
//
//import java.io.IOException;
//
//import java.io.InputStreamReader;
//
//import java.io.Serializable;
// 
//import org.apache.commons.io.FileUtils;
//
//import org.eclipse.jgit.api.Git;
//
//import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
//
//import org.json.simple.JSONArray;
//
//import org.json.simple.JSONObject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.beans.factory.annotation.Value;
//
////import org.springframework.cloud.context.config.annotation.RefreshScope;
//
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.vault.core.VaultTemplate;
//
//import org.springframework.vault.support.VaultResponseSupport;
// 
//import java.nio.file.Files;
//
//import java.nio.file.Path;
//
//import java.nio.file.Paths;
//
//import java.util.HashMap;
//
//import java.util.List;
//
//import java.util.Optional;
//
//import java.util.stream.Collectors;
//
//import java.util.stream.Stream;
// 
//import org.springframework.web.bind.annotation.RequestBody;
//
//import org.springframework.web.bind.annotation.RestController;
// 
//import com.fasterxml.jackson.core.JsonParser;
//
//import com.fasterxml.jackson.databind.JsonNode;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//import com.fasterxml.jackson.databind.node.ArrayNode;
//
//import com.microsoft.terraform.TerraformClient;
//
//import com.tcs.cip.model.Outputmodel;
//
//import com.tcs.cip.model.ScalingDb;
//
//import com.tcs.cip.model.ScalingModel;
//
//import com.tcs.cip.model.Secret;
//
//import com.tcs.cip.model.Serverfarmdetails;
//
//import com.tcs.cip.model.Tenant;
//
//import com.tcs.cip.repos.InstanceDataRepo;
//
//import com.tcs.cip.repos.TenantRepository;
//
//import com.tcs.cip.services.CloudServices;
// 
//import org.slf4j.Logger;
//
//import org.slf4j.LoggerFactory;
// 
// 
////@RefreshScope
//
//@RestController
//
//public class CloudServicesImpl implements CloudServices , Serializable{
//
//	@Autowired
//
//	private TenantRepository tenantrepo;	
//
//	@Autowired
//
//	private InstanceDataRepo instancedata;
//
//	@Autowired
//
//    private VaultTemplate vaultTemplate;
//
// 
//	
//
//
//	int flag=0;
//
//	Logger logger = LoggerFactory.getLogger(CloudServicesImpl.class);
//
//	public static final TerraformClient terraform = new TerraformClient();
//
//
//public void triggerscript(String scriptpath) {
//
//	int flag=0;
//
//	System.out.println("TRIGGERING THE TERRAFORM SCRIPTS FOR SLURM INSTALLATION");
//
//	try {
//
//        this.terraform.setOutputListener(System.out::println);
//
//        this.terraform.setErrorListener(System.err::println);
//
//        this.terraform.setWorkingDirectory(Paths.get(scriptpath));
//
//        if(this.terraform.plan().get().booleanValue()==false) {
//
//     	   flag=1;
//
//        }
//
//        this.terraform.apply().get();
// 
//} catch (Exception ex) {
//
//     ex.printStackTrace();
//
//     flag=1;
//
//}
//
//}
//
//public int TerraformINITOperation(String path){
//
//	 ProcessBuilder processBuilder = new ProcessBuilder();
//
//	 	//String path1="/home/ubuntu/run-scripts/Final-Demo-AWS/slurm-script";
//
//	 	createfiles(path);
//
//	    // -- Linux --
// 
//	    // Run a shell command
//
//	 //	processBuilder.command("bash", "-c", "cd /home/ubuntu/run-scripts/Final-Demo-AWS/slurm-script && terraform init");
//
//	 //   processBuilder.command("bash", "-c", "cd /home/ubuntu/run-scripts/Final-Demo-AWS/slurm-script && terraform refresh");
//
//	    processBuilder.command("bash", "-c", "cd "+path+" && terraform init");
//
//	  //  processBuilder.command("bash", "-c", "cd "+path+" && terraform refresh");
// 
//	    // Run a shell script
//
//	    //processBuilder.command("path/to/hello.sh");
// 
//	    // -- Windows --
// 
//	    // Run a command
//
//	    //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");
// 
//	    // Run a bat file
//
//	    //processBuilder.command("C:\\Users\\mkyong\\hello.bat");
// 
//	    try {
// 
//	        Process process = processBuilder.start();
// 
//	        StringBuilder output = new StringBuilder();
// 
//	        BufferedReader reader = new BufferedReader(
//
//	                new InputStreamReader(process.getInputStream()));
// 
//	        String line;
//
//	        while ((line = reader.readLine()) != null) {
//
//	            output.append(line + "\n");
//
//	        }
// 
//	        int exitVal = process.waitFor();
//
//	        if (exitVal == 0) {
//
//	            System.out.println("Success!");
//
//	            System.out.println(output);
//
//	        } else {
//
//	        	flag=1;
//
//	          System.out.println("Error Occured in refresh operation");
//
//	        }
// 
//	    } catch (IOException e) {
//
//	        e.printStackTrace();
//
//	    } catch (InterruptedException e) {
//
//	        e.printStackTrace();
//
//	    }
//
//	    return 0;
//
//}
//
//public int TerraformRefreshOperation(String path) {
//
//	 ProcessBuilder processBuilder = new ProcessBuilder();
//
//
//	    // -- Linux --
// 
//	    // Run a shell command
//
//	 //	processBuilder.command("bash", "-c", "cd /home/ubuntu/run-scripts/Final-Demo-AWS/slurm-script && terraform init");
//
//	 //   processBuilder.command("bash", "-c", "cd /home/ubuntu/run-scripts/Final-Demo-AWS/slurm-script && terraform refresh");
//
//	   // processBuilder.command("bash", "-c", "cd "+path+" && terraform init");
//
//	    processBuilder.command("bash", "-c", "cd "+path+" && terraform refresh");
// 
//	    // Run a shell script
//
//	    //processBuilder.command("path/to/hello.sh");
// 
//	    // -- Windows --
// 
//	    // Run a command
//
//	    //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");
// 
//	    // Run a bat file
//
//	    //processBuilder.command("C:\\Users\\mkyong\\hello.bat");
// 
//	    try {
// 
//	        Process process = processBuilder.start();
// 
//	        StringBuilder output = new StringBuilder();
// 
//	        BufferedReader reader = new BufferedReader(
//
//	                new InputStreamReader(process.getInputStream()));
// 
//	        String line;
//
//	        while ((line = reader.readLine()) != null) {
//
//	            output.append(line + "\n");
//
//	        }
// 
//	        int exitVal = process.waitFor();
//
//	        if (exitVal == 0) {
//
//	            System.out.println("Success!");
//
//	            System.out.println(output);
//
//	        } else {
//
//	        	flag=1;
//
//	          System.out.println("Error Occured in refresh operation");
//
//	        }
// 
//	    } catch (IOException e) {
//
//	        e.printStackTrace();
//
//	    } catch (InterruptedException e) {
//
//	        e.printStackTrace();
//
//	    }
//
//	    return 0;
//
//}
// 
// 
//public void gitlab(String filename,String branchname,Path path) throws IOException {
// 
//	String repoUrl = "http://gitlab-1933297517.ap-south-1.elb.amazonaws.com/cnipteam/cip-terraform-scripts.git";
//
//	try {
//
//	String cloneDirectoryPath = path.toString();
// 
//		        System.out.println("Cloning "+repoUrl+" into "+repoUrl);
//
//		        Git res;
//
//		res = Git.cloneRepository()
//
//		        .setURI(repoUrl)
//
//		        .setDirectory(Paths.get(cloneDirectoryPath).toFile())
//
//		        .setCredentialsProvider(new UsernamePasswordCredentialsProvider("vivek","vivekgitlab"))
//
//		        .setBranch(branchname)
//
//		        .call();
//
//		System.out.println("Completed Cloning");
//
//        res.getRepository().close();
//
//	} catch (Exception e) {
//
//		flag=1;
//
//		// TODO Auto-generated catch block
//
//		e.printStackTrace();
//
//		System.out.println("Error in Cloning Terraform Scripts from gitlab");
//
//	} 
//
//}
//
//public JSONObject getawsresponse(String filename) {
//
//	int tcpu=0;
//
//	int tmem=0;
//
//	if(flag==1) {
//
//		JSONObject failed=new JSONObject();
//
//		failed.put("context","Create Infra services");
//
//		failed.put("status","failed");
//
//		failed.put("message","Creation failed");
//
//		failed.put("data",null);
//
//		System.out.println(failed);
//
//	}
// 
//	JSONObject response=new JSONObject();
//
//	ObjectMapper mapper = new ObjectMapper();
//
//	JsonNode root = null;
//
//	File from = new File(filename+"/modules/studio/on-aws/slurm/terraform.tfstate");
//
//	//File from = new File("C:\\Users\\MANOHAR\\Desktop\\run-scripts\\awsterraform.tfstate");
//
//	try {
//
//		 root = mapper.readTree(from);
//
//	}
//
//	catch(Exception e) {
//
//		e.printStackTrace();
//
//	}
//
//	JsonNode resources=root.get("resources");
//
//	ArrayNode slavedetails=(ArrayNode) resources.get(1).get("instances");
//
//	ArrayNode masterdetails=(ArrayNode) resources.get(2).get("instances");
//
//
//	//System.out.println("enterd");
//
//
//	int cs=1;
//
//	HashMap<String, String> master = new HashMap<String, String>();
//
//	JSONObject clusterdetails=new JSONObject();
//
//	List<HashMap> computedetails = new ArrayList<HashMap>();
//
//
//	tcpu=tcpu+masterdetails.get(0).get("attributes").get("cpu_core_count").asInt();
//
//	tmem=tmem+masterdetails.get(0).get("attributes").get("root_block_device").get(0).get("volume_size").asInt();
//
//
//		master.put("servername",masterdetails.get(0).get("index_key").asText());
//
//		master.put("ip",masterdetails.get(0).get("attributes").get("private_ip").asText());
//
//		master.put("username","Ubuntu");
//
//		master.put("password", "ubuntu");
//
//	//	System.out.println(masterdetails.get(0).get("attributes").get("cpu_core_count").asInt());
//
//		clusterdetails.put("masterDetails",master);
//
//		cs=cs+slavedetails.size();
//
//		for(int i=0;i<slavedetails.size();i++) {
//
//		//	System.out.println("slave en");
//
//			HashMap<String, String> slave = new HashMap<String, String>();
//
//			tcpu=tcpu+slavedetails.get(i).get("attributes").get("cpu_core_count").asInt();
//
//			tmem=tmem+slavedetails.get(i).get("attributes").get("root_block_device").get(0).get("volume_size").asInt();
//
//			slave.put("servername",slavedetails.get(i).get("index_key").asText());
//
//			slave.put("ip",slavedetails.get(i).get("attributes").get("private_ip").asText());
//
//			slave.put("username","Ubuntu");
//
//			slave.put("password", "ubuntu");
//
//			computedetails.add(slave);
//
//		}
//
//		clusterdetails.put("computeDetails", computedetails);
//
//		JSONObject data=new JSONObject();
//
//		JSONObject clusterSpecifications=new JSONObject();
//
//
//		response.put("context","Create Infra services");
//
//		response.put("status","success");
//
//		response.put("message","Created Infra services");
//
//		data.put("clusterName","Clustername");
//
//		data.put("clustertype","hpc");
//
//		data.put("clusterSize",cs);
//
//		data.put("clusterRootApi", "http://test:5000");
//
//		data.put("clusterLocation","onCloud");
//
//		data.put("cloudType","Aws");
//
//		data.put("clusterScheduler", "Disabled");
//
//		data.put("schedulerType","Slurm");
//
//		 clusterSpecifications.put("totalVCpus",tcpu);
//
//		 String mem=Integer.toString(tmem);
//
//		 mem=mem+"GiB";
//
//		 clusterSpecifications.put("totalMemory",mem);
//
//		 data.put("clusterSpecifications",clusterSpecifications);
//
//
//		 data.put("clusterDetails",clusterdetails);
//
//		 response.put("data", data);
//
//		 System.out.println("Response"+response);
//
//		 System.out.println(response);
//
//	return response;
//
//}
//
//public void storedata(Tenant tenantDetail,String filename) {
//
//	Outputmodel obj=new Outputmodel();
//
//	obj.setTenantName(tenantDetail.getTenantName());
//
//	obj.setServerFarmName(tenantDetail.getServerFarmName());
//
//	File from = new File(filename+"/modules/studio/on-aws/slurm/terraform.tfstate");
//
//	HashMap <String,Object > statefile=null;
//
//	try {
//
//	 statefile= new ObjectMapper().readValue(from, HashMap.class);
//
//	}
//
//	catch(Exception E) {
//
//		E.printStackTrace();
//
//	}
//
//	JSONObject response=new JSONObject();
//
//	ObjectMapper mapper = new ObjectMapper();
// 
// 
//	obj.setStatefile(statefile);
// 
//	tenantrepo.save(obj);
//
//System.out.println("Data  "+obj);
//
//}
//
//@Value("${message:Default-Message}")
//
//public String msg;
// 
// 
//public void StorePemKeyIntoVault(String filename,String tenantname) {
//
//	HashMap<String,Object> hashMap=new HashMap<String, Object>();
//
//	Stream<String> lines = null;
//
//	try {
//
//		lines = Files.lines(Paths.get(filename+"/modules/aws/compute/hpc/slurm/staging/slurmkeypair.pem"));
//
//	} catch (IOException e) {
//
//		// TODO Auto-generated catch block
//
//		e.printStackTrace();
//
//	}
//
//	String content = lines.collect(Collectors.joining(System.lineSeparator()));
//
//	System.out.println(content);
//
//	hashMap.put("slurmkp",content);
//
//	JSONObject jsonObject=new JSONObject(hashMap);
//
//	System.out.println(jsonObject);
//
//	//path is tenant-name/mastername
//
//	// vaultTemplate.write("/kv/data/"+tenantname,jsonObject);
//
//	// vaultTemplate.write("/kv/data/vivek",jsonObject);
//
//	vaultTemplate.write("cubbyhole/kv/"+tenantname,jsonObject);
//
//
//	// Secrets secrets = new Secrets();
//
////   secrets.username = "slurmkp";
//
////   secrets.password = content;
//
////   vaultTemplate.write("/kv/data/vivek/*", secrets);
//
//}
// 
// 
//public void FetchPemKeyFromVault(String filename,String tenantname) {
//
//	VaultResponseSupport<Secret> responseSupport=vaultTemplate.read("/kv/"+tenantname, Secret.class);
//
//    String slurmkp=responseSupport.getData().getSlurmkp();
//
//    try {
//
//		FileUtils.writeStringToFile(new File(filename+"/scaling-script/slurmkeypair"), slurmkp);
//
//	} catch (IOException e) {
//
//		e.printStackTrace();
//
//	}
//
//}
//
//public JSONObject testing(Tenant tobj ) {
//
//JSONObject obj=new JSONObject();
//
//System.out.println(obj);
//
//	obj.put("tenant", tobj);
//
//return obj;
//
//}
// 
//public void deleteaws() {
//
//}
//
//public void deletegcp() {
//
//
//}
//
//public void awscopykeys(String filename) {
//
//	//write code to get variables.tf file from vault
//
//	VaultResponseSupport<Secret> responseSupport=vaultTemplate.read("/kv/coreservice/variables", Secret.class);
//
//	String slurmkp=responseSupport.getData().getSlurmkp();
//
//	try {
//
//		FileUtils.writeStringToFile(new File(filename+"/slurm-script/variables.tf"), slurmkp);
//
//	} catch (IOException e) {
//
//		// TODO Auto-generated catch block
//
//		e.printStackTrace();
//
//	}
// 
// 
//	
//
//}
//
//public void gcpcopyfile(String filename) {
//
//	File source1 = new File("/home/ubuntu/KEYS/slurm.conf.tmp");
//
//	File dest1 = new File("/home/ubuntu/UPDATED-TERRAFORM-SCRIPTS/slurm.conf.tmp");
//
//	try {
//
//		Files.copy(source1.toPath(), dest1.toPath());
//
//	} catch (IOException e) {
//
//		// TODO Auto-generated catch block
//
//		e.printStackTrace();
//
//	}
//
//
//}
//
//public JSONObject gcpresponse(String filename) {
//
//	if(flag==1) {
//
//		JSONObject failed=new JSONObject();
//
//		failed.put("context","Create Infra services");
//
//		failed.put("status","failed");
//
//		failed.put("message","Creation failed");
//
//		failed.put("data",null);
//
//		System.out.println(failed);
//
//	}
// 
//	
//
//	int tcpu=4;
//
//	int scpu=2;
//
//	int tmem=0;
//
//	int cs=1;
//
//	JSONObject response=new JSONObject();
//
//	ObjectMapper mapper = new ObjectMapper();
//
//	JsonNode root = null;
//
//	System.out.println("EN1");
//
//	File from = new File("/home/ubuntu/run-scripts/"+filename+"/slurm-script/terraform.tfstate");
//
//	//File from = new File("C:\\Users\\MANOHAR\\Desktop\\run-scripts\\gcpterraform.tfstate");
//
//	System.out.println("EN2");
//
//	try {
//
//		 root = mapper.readTree(from);
//
//	}
//
//	catch(Exception e) {
//
//		e.printStackTrace();
//
//	}
//
//	HashMap<String, String> master = new HashMap<String, String>();
//
//	JSONObject clusterdetails=new JSONObject();
//
//	List<HashMap> computedetails = new ArrayList<HashMap>();
//
//
//	JsonNode resources=root.get("resources");
//
//	ArrayNode slavedetails=(ArrayNode) resources.get(0).get("instances");
//
//	ArrayNode masterdetails=(ArrayNode) resources.get(1).get("instances");
//
//	scpu=scpu*(slavedetails.size());
//
//	tcpu=tcpu+scpu;
//
//	tmem=tmem+masterdetails.get(0).get("attributes").get("boot_disk").get(0).get("initialize_params").get(0).get("size").asInt();
//
//	master.put("servername",masterdetails.get(0).get("attributes").get("name").asText());
//
//	master.put("ip",masterdetails.get(0).get("attributes").get("network_interface").get(0).get("access_config").get(0).get("nat_ip").asText());
//
//	master.put("username","Ubuntu");
//
//	master.put("password", "ubuntu");
//
//	 cs=cs+slavedetails.size();
//
//	 clusterdetails.put("masterDetails",master);
//
//	 for(int i=0;i<slavedetails.size();i++) {
//
//			//	System.out.println("slave en");
//
//				HashMap<String, String> slave = new HashMap<String, String>();
//
//				tmem=tmem+slavedetails.get(i).get("attributes").get("boot_disk").get(0).get("initialize_params").get(0).get("size").asInt();
//
//				slave.put("servername",slavedetails.get(i).get("attributes").get("name").asText());
//
//				slave.put("ip",slavedetails.get(i).get("attributes").get("network_interface").get(0).get("access_config").get(0).get("nat_ip").asText());
//
//				slave.put("username","Ubuntu");
//
//				slave.put("password", "ubuntu");
//
//				computedetails.add(slave);
//
//			}
//
//	 clusterdetails.put("computeDetails", computedetails);
//
//		JSONObject data=new JSONObject();
//
//		JSONObject clusterSpecifications=new JSONObject();
//
//
//		response.put("context","Create Infra services");
//
//		response.put("status","success");
//
//		response.put("message","Created Infra services");
//
//		data.put("clusterName","Clustername");
//
//		data.put("clustertype","hpc");
//
//		data.put("clusterSize",cs);
//
//		data.put("clusterRootApi", "http://test:5000");
//
//		data.put("clusterLocation","onCloud");
//
//		data.put("cloudType","Gcp");
//
//		data.put("clusterScheduler", "Disabled");
//
//		data.put("schedulerType","Slurm");
//
//		 clusterSpecifications.put("totalVCpus",tcpu);
//
//		 String mem=Integer.toString(tmem);
//
//		 mem=mem+"GiB";
//
//		 clusterSpecifications.put("totalMemory",mem);
//
//		 data.put("clusterSpecifications",clusterSpecifications);
//
//
//		 data.put("clusterDetails",clusterdetails);
//
//		 response.put("data", data);
//
//		 System.out.println("Response"+response);
//
//		 System.out.println(response);
//
//	return response;
//
//
//}
//
//public JSONObject gcpresources(Serverfarmdetails sobj,String filename) {
//
//	String branchname="dev11.0";
//
//	System.out.println("CLONING THE TERRAFORM SCRIPTS FROM GIT STARTED");
//
//	System.out.println("CREATING RESOURCES ON THE CLOUD FOR THE TENANT"+filename);
//
///*	try {
//
//		gitlab(filename,branchname);
//
//	} catch (IOException e1) {
//
//		e1.printStackTrace();
//
//	}*/
//
//	System.out.println("CLONING COMPLETED");
//
//	JsonNode listofzones=sobj.getAvailability();
//
//	ArrayNode zones=(ArrayNode) listofzones.get("locations");
//
//	String zoneval=zones.get(0).asText();
//
//	JSONObject response=new JSONObject();
//
//	HashMap<String,Object> fobj=new HashMap<String,Object>();
//
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	 objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//
//	try {
//
//		fobj.put("region",zoneval);
//
//		fobj.put("resources", sobj.getResources());
//
//		objectMapper.writeValue(new File("/home/ubuntu/run-scripts/"+filename+"/slurm-script/input.json"),fobj);
//
//	}
//
//	catch(Exception E) {
//
//		E.printStackTrace();
//
//	}
//
//	String triggerpath="/home/ubuntu/run-scripts/"+filename+"/slurm-script";
//
//	gcpcopyfile(filename);
//
//	triggerscript(triggerpath);
//
//	deletegcp();
//
//	response=gcpresponse(filename);
//
//	return response;
//
//
//}
// 
//public JSONObject awsresources(Serverfarmdetails sobj,String filename,String tenantname) {
//
//	//setawsinfrastructure();
//
//	CreateDirectoriesAndClone(filename,0,"AWS");
//
//	System.out.println("CLONING THE TERRAFORM SCRIPTS FROM GIT STARTED");
//
//	System.out.println("CREATING RESOURCES ON THE CLOUD FOR THE TENANT"+filename);
//
//	HashMap<String,List> obj=new HashMap<String,List>();
//
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	 objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//
//	 JSONObject response=new JSONObject();
//
//	 try {
//
//		 obj.put("resources", sobj.getResources());
//
//			objectMapper.writeValue(new File(filename+"/modules/studio/on-aws/input.json"),obj);
//
//	 }
//
//	 catch(Exception E) {
//
//		 E.printStackTrace();
//
//	 }
//
//	String triggerpath=filename+"/modules/studio/on-aws/bastion";
//
//	// awscopykeys(filename);
//
//	triggerscript(triggerpath);
//
//	StorePemKeyIntoVault(filename,tenantname);
//
//	//deleteaws();
//
//	 response=getawsresponse(filename);
//
//	return response;
//
//}
//
//public void createfiles(String path) {
//
//	File f1=new File(path+"/input.json");
//
//}
//
//public String getprovider(ScalingModel Nodedetails) {
//
//	String provider=Nodedetails.getCloud().toLowerCase();
//
//	if(provider.contains("amazon")) {
//
//		return "AWS";
//
//	}
//
//	else if(provider.contains("azure")) {
//
//		return "AZURE";
//
//	}
//
//	else {
//
//		return "GCP";
//
//	}
//
//}
// 
// 
//public HashMap<String,Object> getstatefiledetails(ScalingModel Nodedetails,String filename,String region) {
//
//	//This function will create a terraform.tfstate file inside a folder 
//
//	//This function will fetch the statefile from the mongodb
//
//	//This function gives all the public address and master private address from the state file
//
//	String provider=getprovider(Nodedetails);
//
//	JSONObject regionjson=new JSONObject();
//
//	regionjson.put("region",region);
//
//	HashMap<String,Object> ips=new HashMap<String,Object>();
//
//	 List<String> Mpubips=new ArrayList<String>();
//
//	 List<String> Spubips=new ArrayList<String>();
//
//	   String privateip;
//
//
//
//
//	Optional<Outputmodel> out=Optional.ofNullable(new Outputmodel());
//
//	 out=tenantrepo.findByTenantNameAndServerFarmName(Nodedetails.getTenantName(),Nodedetails.getServerFarmDetails().getServerFarmName());
// 
//	 System.out.println("Data  "+out);
// 
//	 ObjectMapper objectMapper = new ObjectMapper();
//
//	 objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//
//	 //writing statefile data 
//
//	 try {
//
//	 	objectMapper.writeValue(new File(filename+"/refresh-data/"+provider+"/terraform.tfstate"),out.get().getStatefile());
//
//	 	//path C:\\Users\\MANOHAR\\Desktop\\run-scripts\\"+filename+"\\terraform.tfstate"
//
//	 }
//
//	 catch(Exception E) {
//
//	 	E.printStackTrace();
//
//	 }
//
//	 //writing region to input.json
//
//	 try {
//
//		 objectMapper.writeValue(new File(filename+"/refresh-data/"+provider+"/input.json"),regionjson);
//
//	 }
//
//	 catch(Exception E) {
//
//		 E.printStackTrace();
//
//	 }
//
//	 CopyTerraKeyForScaling(filename,provider);
//
//	 String path=filename+"/refresh-data/"+provider;
//
//	 TerraformINITOperation(path);
//
//	 TerraformRefreshOperation(path);
//
//	 	JsonNode root = null;
//
//		//File from = new File("/home/ubuntu/run-scripts/"+filename+"/terraform.tfstate");
//
//	 	File from = new File(filename+"/refresh-data/"+provider+"/terraform.tfstate");
//
//	 	System.out.println("reading data from statefile");
//
//		JSONObject response=new JSONObject();
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		try {
//
//			 root = mapper.readTree(from);
//
//		}
//
//		catch(Exception e) {
//
//			e.printStackTrace();
//
//		}
//
//		System.out.println("completed reading data from statefile");
//
//		JsonNode resources=root.get("resources");
//
//		ArrayNode slavedetails=(ArrayNode) resources.get(0).get("instances");
//
//		ArrayNode masterdetails=(ArrayNode) resources.get(1).get("instances");
//
//		System.out.println("Fetching data from statefile");
//
//		for(int i=0;i<slavedetails.size();i++) {	
//
//				Spubips.add(slavedetails.get(i).get("attributes").get("public_ip").asText());
//
//			}
//
//		for(int i=0;i<masterdetails.size();i++) {	
//
//			Mpubips.add(masterdetails.get(i).get("attributes").get("public_ip").asText());
//
//		}
//
//		privateip=masterdetails.get(0).get("attributes").get("private_ip").asText();
//
//	ips.put("volume_type",masterdetails.get(0).get("attributes").get("root_block_device").get(0).get("volume_type"));
//
//	ips.put("Master_public_ip", Mpubips);
//
//	ips.put("Slave_public_ip", Spubips);
//
//	ips.put("private_ip",privateip);
//
//	ips.put("master_dns",masterdetails.get(0).get("attributes").get("public_dns").asText());
//
//	ips.put("Security-group",masterdetails.get(0).get("attributes").get("vpc_security_group_ids").get(0).asText());
//
//	ips.put("SubnetID",masterdetails.get(0).get("attributes").get("subnet_id").asText());
//
//	ips.put("ami",slavedetails.get(0).get("attributes").get("ami").asText());
//
//	ips.put("keyname",masterdetails.get(0).get("attributes").get("key_name").asText());
//
//
//	return ips;
//
//}
// 
// 
//public void FetchDetailsandWriteInput(ScalingModel Nodedetails) {
//
//	String filename=Nodedetails.getTenantName()+"-"+Nodedetails.getServerFarmDetails().getServerFarmID();
//
//	System.out.println("Filename   ------->" +filename);
//
//	 HashMap<String,Object> input=new HashMap<String,Object>();
//
//	 JSONObject res=new JSONObject();
//
//	 JSONArray list = new JSONArray();
//
//	 int mem;
//
//	int storage;
//
//	int cores;
//
//	 String tmp;
//
//	 ObjectMapper mapper = new ObjectMapper();
//
//		JsonNode root = null;
//
//		ArrayNode resources = null;
//
//		JsonNode fa=null;
//
//		try {
//
//			root=mapper.valueToTree(Nodedetails.getServerFarmDetails());
//
//		}
//
//		catch(Exception e) {
//
//			e.printStackTrace();
//
//		}
//
//		resources=(ArrayNode)root.get("resources");
//
//	for(JsonNode node:resources) {
//
//		 JSONObject sr=new JSONObject();
//
//		sr.put("nodename",node.get("nodeName"));
//
//		sr.put("cores",node.get("cores").asInt());
//
//		tmp=node.get("memory").toString();
//
//		tmp=tmp.substring(1, tmp.length()-3);
//
//		mem=Integer.parseInt(tmp);
//
//		sr.put("memory",mem);
//
//		tmp=null;
//
//		tmp=node.get("storage").toString();
//
//		tmp=tmp.substring(1, tmp.length()-3);
//
//		storage=Integer.parseInt(tmp);
//
//		tmp=null;
//
//		sr.put("storage",storage);
//
//		sr.put("region",node.get("region"));
//
//		sr.put("zone",node.get("availabilityZone"));
//
//		sr.put("partition",node.get("partition"));
//
//		sr.put("count",node.get("count"));
//
//		cores=node.get("cores").asInt();
//
//		List<ScalingDb> data=instancedata.findBycoresmemory(cores-1, mem-1); 
//
//		sr.put("instance_type",data.get(0).getInstance_type());
//
//	 list.add(sr);
//
//
//
//	}
//
//	//This is to get master private-ip and all the nodes public ips
//
//	String region=resources.get(0).get("region").asText();
//
//	input=getstatefiledetails(Nodedetails,filename,region);
//
//	//input.put("resources",list);
//
//	res.put("resources",list);
//
//	res.put("volume_type",input.get("volume_type"));
//
//	res.put("Master_public_ip",input.get("Master_public_ip"));
//
//	res.put("Slave_public_ip",input.get("Slave_public_ip"));
//
//	res.put("master_dns", input.get("master_dns"));
//
//	res.put("private_ip",input.get("private_ip"));
//
//	res.put("SecurityGroup",input.get("Security-group"));
//
//	res.put("SubnetID", input.get("SubnetID"));
//
//	res.put("ami",input.get("ami"));
//
//	res.put("key_name",input.get("keyname"));
//
//
//
//	 mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//
//	try {
//
//		//mapper.writeValue(new File("C:\\Users\\MANOHAR\\Desktop\\run-scripts\\scaling-script\\input.json"),res);
//
//		mapper.writeValue(new File(filename+"/scaling-script/input.json"),res);
//
//	}
//
//	catch(Exception E) {
//
//		E.printStackTrace();
//
//	}
//
//}
//
//public void CopyTerraKeyForScaling(String filename,String provider) {
//
//
//	VaultResponseSupport<Secret> responseSupport=vaultTemplate.read("/kv/coreservice/variables", Secret.class);
//
//	String slurmkp=responseSupport.getData().getSlurmkp();
//
//	try {
//
//		FileUtils.writeStringToFile(new File(filename+"/refresh-data/"+provider+"/variables.tf"), slurmkp);
//
//	} catch (IOException e) {
//
//		// TODO Auto-generated catch block
//
//		e.printStackTrace();
//
//	}
//
//	try {
//
//		FileUtils.writeStringToFile(new File(filename+"/scaling-script/variables.tf"), slurmkp);
//
//	} catch (IOException e) {
//
//		// TODO Auto-generated catch block
//
//		e.printStackTrace();
//
//	}
//
//}
//
//public JSONObject getscaleapiresponse(int flag,ScalingModel Nodedetails) {
//
//	JSONObject response=new JSONObject();
//
//	String filename=Nodedetails.getTenantName()+"-"+Nodedetails.getServerFarmDetails().getServerFarmID();
//
//	String provider=getprovider(Nodedetails);
//
//	if(flag==0) {
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		JsonNode root = null;
//
//		File from = new File(filename+"/refresh-data/"+provider+"/terraform.tfstate");
//
//		try {
//
//			 root = mapper.readTree(from);
//
//		}
//
//		catch(Exception e) {
//
//			e.printStackTrace();
//
//		}
//
//		JsonNode resources=root.get("resources");
//
//		ArrayNode slavedetails=(ArrayNode) resources.get(0).get("instances");
//
//		ArrayNode masterdetails=(ArrayNode) resources.get(1).get("instances");
//
//
//		//System.out.println("enterd");
//
//
//		int cs=1;
//
//		HashMap<String, String> master = new HashMap<String, String>();
//
//		JSONObject clusterdetails=new JSONObject();
//
//		List<HashMap> computedetails = new ArrayList<HashMap>();
//
//
//
//			master.put("servername",masterdetails.get(0).get("index_key").asText());
//
//			master.put("ip",masterdetails.get(0).get("attributes").get("private_ip").asText());
//
//			master.put("username","Ubuntu");
//
//			master.put("password", "ubuntu");
//
//		//	System.out.println(masterdetails.get(0).get("attributes").get("cpu_core_count").asInt());
//
//			clusterdetails.put("masterDetails",master);
//
//			cs=cs+slavedetails.size();
//
//			for(int i=0;i<slavedetails.size();i++) {
//
//			//	System.out.println("slave en");
//
//				HashMap<String, String> slave = new HashMap<String, String>();
//
//
//				slave.put("servername",slavedetails.get(i).get("index_key").asText());
//
//				slave.put("ip",slavedetails.get(i).get("attributes").get("private_ip").asText());
//
//				slave.put("username","Ubuntu");
//
//				slave.put("password", "ubuntu");
//
//				computedetails.add(slave);
//
//			}
//
//			clusterdetails.put("computeDetails", computedetails);
//
//			JSONObject data=new JSONObject();
//
//			JSONObject clusterSpecifications=new JSONObject();
//
//
//			response.put("context","Create Infra services");
//
//			response.put("status","success");
//
//			response.put("message","Created Infra services"); 
//
//
//			File newnodefile = new File(filename+"/scaling-script/terraform.tfstate");
//
//			try {
//
//				 root = mapper.readTree(newnodefile);
//
//			}
//
//			catch(Exception e) {
//
//				e.printStackTrace();
//
//			}
//
//			JsonNode newresources=root.get("resources");
//
//			ArrayNode newcluster=(ArrayNode) newresources.get(1).get("instances");
//
//			for(int i=0;i<newcluster.size();i++) {
//
//				//	System.out.println("slave en");
//
//					HashMap<String, String> cd = new HashMap<String, String>();
//
//
//					cd.put("servername",newcluster.get(i).get("index_key").asText());
//
//					cd.put("ip",newcluster.get(i).get("attributes").get("private_ip").asText());
//
//					cd.put("username","Ubuntu");
//
//					cd.put("password", "ubuntu");
//
//					computedetails.add(cd);
//
//				}
// 
//			
//
//			 data.put("clusterDetails",clusterdetails);
//
//			 response.put("data", data);
//
//		return response;
//
//	}
//
//	else {
//
//	response.put("context","update Infra Services");
//
//	response.put("status", "failed");
//
//	response.put("message","Update Infra services failed");
//
//	response.put("data",null);
//
//		return response;
//
//	}
//
//}
//
//public void CreateDirectoriesAndClone(String filename,int flag,String provider) {
//
//	// 0 for slurm -cluster api
//
//	//1 for scaling api
//
//	String slurmbranch = null;
//
//	String refreshbranch=null;
//
//	String scalingbranch=null;
//
//	if(provider.equalsIgnoreCase("AWS")) {
//
//		if(flag==0) {
//
//			slurmbranch="studio";
//
//		}
//
//		else {
//
//			refreshbranch="refresh-script";
//
//			 scalingbranch="scaling-script";
//
//		}
//
//	}
//
//	if(flag==0) {
//
//		try {
//
//			Path path= Files.createDirectories(Paths.get(filename));
//
//			// gitlab(filename,slurmbranch,path);
//
//		}
//
//		catch(Exception E) {
//
//			E.printStackTrace();
//
//		}
//
//	}
//
//	else {
//
//		try {
//
//			Path path= Files.createDirectories(Paths.get(filename));
//
//			Path path1= Files.createDirectories(Paths.get(filename+"/refresh-data"));
//
//			Path path2= Files.createDirectories(Paths.get(filename+"/scaling-script"));
//
//				gitlab(filename,refreshbranch,path1);
//
//				gitlab(filename,scalingbranch,path2);
//
//
//		}
//
//		catch(Exception E) {
//
//			E.printStackTrace();
//
//		}
//
//	}
//
//}
//
//public JSONObject ScaleSlurm( ScalingModel Nodedetails,String id) {
//
//	JSONObject response=new JSONObject();
//
//	String filename=Nodedetails.getTenantName()+"-"+Nodedetails.getServerFarmDetails().getServerFarmID();
//
//	CreateDirectoriesAndClone(filename,1,"AWS");
//
//	flag=0;
//
//	FetchDetailsandWriteInput(Nodedetails);
//
//	FetchPemKeyFromVault(filename,Nodedetails.getTenantName());
//
////	copyterraformkey(filename);
//
//	String triggerpath=filename+"/scaling-script/";
//
//	triggerscript(triggerpath);
//
//	//StorePemKeyIntoVault(filename);
//
//	//res=getscaleapiresponse();
//
//	response=getscaleapiresponse(flag,Nodedetails);
//
//	DeleteClonedFiles(filename);
//
//	return response;
//
//}
//
//	public void DeleteClonedFiles(String filename) {
//
//		//File src=new File(filename);
//
//		File src=new File(filename);
//
//		 try {
//
//			 FileUtils.deleteDirectory(src);
//
//		 }
//
//		 catch(Exception E) {
//
//			 E.printStackTrace();
//
//		 }
//
//	}
//
//	public JSONObject  createCloudServices(Tenant tenantDetail) {
//
//		int awsflag=0,gcpflag=0;
//
//		System.out.println("***********Started CoreService Application************");
//
//		String tenantname=tenantDetail.getTenantName();
//
//		String filename=tenantname;
//
//		List<Serverfarmdetails> sobj=  tenantDetail.getServerFarmDetails();
//
//
//		JSONObject response=new JSONObject();
//
//		JSONObject lresponse=new JSONObject();
//
//		JSONObject finalresponse=new JSONObject();
//
//		JSONObject returnres=new JSONObject();
//
//		for(int i=0;i<sobj.size();i++) {
//
//			awsflag=0;
//
//			gcpflag=0;
//
//			if(sobj.get(i).getCloudService().equals("GCE")) {
//
//				flag=0;
//
//				gcpflag=1;
//
//				filename=filename+"-GCE";
//
//				response=gcpresources(sobj.get(i),filename);
//
//				System.out.println("STORING TENANT SPECIFIC DATA IN MONGODB");
//
//				System.out.println("The flag value is "+flag);
//
//				System.out.println("Response inside for loop " +response);
//
//				storedata(tenantDetail,filename);
//
//			}
//
//			else {
//
//				flag=0;
//
//				awsflag=1;
//
//				filename=filename+"-AWS";
//
//				response=awsresources(sobj.get(i),filename,tenantname);
//
//				System.out.println("STORING TENANT SPECIFIC DATA IN MONGODB");
//
//				System.out.println("The flag value is "+flag);
//
//				System.out.println("Response inside for loop " +response);
//
//				storedata(tenantDetail,filename);
//
//				// DeleteClonedFiles(filename);
//
//			}
//
//			filename=null;
//
//			filename=tenantname;
//
//			if(sobj.size()>1) {
//
//				if(awsflag==1) {
//
//					finalresponse.put("AWS", response);
//
//				}
//
//				else {
//
//					finalresponse.put("GCP", response);
//
//				}
//
//				lresponse=response;
//
//				response.clear();
//
//			}
//
//		}
//
//		if(sobj.size()==1) {
//
//			System.out.println("Final-Response" +response);
//
//			returnres= response;
//
//		}
//
//		else {
//
//			System.out.println("Final-Response" +response);
//
//			returnres= finalresponse;
//
//		}
//
//
//		return returnres;
//
//	}
//
// 
//	public/* HashMap<String, Object>*/  Optional<Tenant> getCloudServicesByTenant(String tenantID) {
//
//		// TODO Auto-generated method stub
//
//		return null;
//
//
//	}
// 
//	public List<String> getCloudServiceByCloudType(String cloudType) {
//
//		// TODO Auto-generated method stub
//
//		return null;
//
//	}
// 
//	public JSONObject deleteCloudService(String serverfarmname,String tenantname,String region, String cloudtype,String credentials) {
//
//		String filename=tenantname;
//
//		int flag=0;
//
//		if(cloudtype.equalsIgnoreCase("AWS")) {
//
//			filename=filename+"-AWS";
//
//		}
//
//		else {
//
//			filename=filename+"-GCE";
//
//		}
//
//		JSONObject response=new JSONObject();
//
//		JSONObject data=new JSONObject();
//
//		try {
//
//            this.terraform.setOutputListener(System.out::println);
//
//	           this.terraform.setErrorListener(System.err::println);
//
//	           this.terraform.setWorkingDirectory(Paths.get("/home/ubuntu/run-scripts/"+filename+"/slurm-script"));
//
//	           if(this.terraform.destroy().get().booleanValue()==false) {
//
//	        	   flag=1;
//
//	           }
// 
//     } catch (Exception ex) {
//
//         ex.printStackTrace();
//
//         flag=1;
//
//     }
//
//		if(flag==0) {
//
//			response.put("context","Delete Infra services");
//
//			response.put("status","success");
//
//			response.put("message","Deleted Infra services");
//
//			data.put("serverFarmName",serverfarmname);
//
//			data.put("tenantName",tenantname);
//
//			data.put("region",region);
//
//			response.put("data",data);
//
//		}
//
//		else {
//
//			response.put("context","Delete Infra services");
//
//			response.put("status","failure");
//
//			response.put("message","Deleted Infra services");
//
//			response.put("data","data");
//
//		}
//
//		return response;
//
//	}
//
//}
