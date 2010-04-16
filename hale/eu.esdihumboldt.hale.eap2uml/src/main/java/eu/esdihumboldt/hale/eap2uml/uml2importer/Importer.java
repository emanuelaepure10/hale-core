package eu.esdihumboldt.hale.eap2uml.uml2importer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.*;
import org.geotools.xml.EMFUtils;


public class Importer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  // Create a resource set.
		  ResourceSet resourceSet = new ResourceSetImpl();
		  // Register the default resource factory -- only needed for stand-alone!
		  //resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		  resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		  // This is a patch to allow UML2 2.1.0 functionality
		  resourceSet.getPackageRegistry().put("http://www.eclipse.org/uml2/2.1.0/UML", UMLPackage.eINSTANCE);

		  // Get the URI of the model file.
		  URI fileURI = URI.createFileURI(new File("adress.xmi").getAbsolutePath());
		  		  
		  Resource resource = resourceSet.getResource(fileURI, true);	  
		  		   
		  // Print the contents of the resource to System.out.
		  //resource.save(System.out, Collections.EMPTY_MAP);
		  
		  // Load the resource into EObject
		  try {
			  
			  resource.load(null);
			  
			  List<EObject> contenidos = resource.getContents();
			  for (int i=0;i<contenidos.size();i++)
			  {
				  EObject ob = contenidos.get(i);
				  System.out.println("Name: "+EMFUtils.get(ob, "name"));
				  System.out.println("Type: "+ob.eClass().getName());
				  List<EObject> obs=ob.eContents();
				  for (int j=0;j<obs.size();j++)
				  {
					  EObject obss = obs.get(j);
					  System.out.println("Name: "+EMFUtils.get(obss, "name"));
					  System.out.println("Type: "+obss.eClass().getName());
				  }
			  }
			  System.out.println("Charged succesful");
		  } catch (IOException e) 
		  {
			  System.out.println("Error while charging xmi/xml file");
			  return;
		  }
		}
}
