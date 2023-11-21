package genepi.nf.test.vcf;

import java.nio.file.Path;

import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

public class Methods {

	public static void helloVCF() {
		System.out.println("Hello VCF");
	}
	
	public static VCFHeader getVcfHeader(Path vcfFilename) {
		VCFFileReader reader = new VCFFileReader(vcfFilename, false);
		VCFHeader header = reader.getFileHeader();
		reader.close();
		return header;
	}
	
}
