package genepi.nf.test.vcf;

import java.nio.file.Path;

import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;

public class Methods {

	public static void helloVCF() {
		System.out.println("Hello VCF");
	}

	public static VariantContext getVcfLine(Path vcfFilename, int pos) {
		VCFFileReader reader = new VCFFileReader(vcfFilename, false);
		CloseableIterator<VariantContext> it = reader.iterator();

		while (it.hasNext()) {

			VariantContext line = it.next();

			if (line.getStart() == pos) {
				reader.close();
				return line;
			}
		}
		reader.close();
		return null;
	}
}
