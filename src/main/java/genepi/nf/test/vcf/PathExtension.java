package genepi.nf.test.vcf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

public class PathExtension {

	public static Object getVcf(Path self) throws FileNotFoundException, IOException {
		return VcfFileUtil.load(self);
		
	}
	
	public static VCFHeader getVcfHeader(Path vcfFilename) {
		VCFFileReader reader = new VCFFileReader(vcfFilename, false);
		VCFHeader header = reader.getFileHeader();
		reader.close();
		return header;
	}
	
}
