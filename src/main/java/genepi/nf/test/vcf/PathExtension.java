package genepi.nf.test.vcf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class PathExtension {

	public static VcfFile getVcf(Path self) throws FileNotFoundException, IOException {
		return VcfFileUtil.load(self);
		
	}
	
}
