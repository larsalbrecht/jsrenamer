/**
 *
 */
package com.lars_albrecht.java.jsrenamer;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;

/**
 * @author lalbrecht
 */
public class MainCommandline {

	/**
	 * > jsrenamer -f "fileA, fileB, fileC"
	 *
	 * @param args
	 * 		The arguments to start with
	 */
	public MainCommandline(final String[] args) {
		final Option fileOpt = new Option("f", "files", true, "List of files");
		fileOpt.setRequired(true);
		final Option pathOpt = new Option("p", "path", true, "Path to files (max 10)");
		pathOpt.setArgs(10);
		final Option recursiveOpt = new Option("r", true, "Recursive");
		final Option filterOpt    = new Option("fi", "filter", true, "Regular expression for a filter to select files");

		final Options     options     = new Options();
		final OptionGroup fileListArg = new OptionGroup();
		fileListArg.addOption(fileOpt);
		fileListArg.addOption(filterOpt);

		final OptionGroup pathArg = new OptionGroup();
		fileListArg.addOption(pathOpt);
		fileListArg.addOption(recursiveOpt);
		fileListArg.addOption(filterOpt);

		options.addOptionGroup(fileListArg);
		options.addOptionGroup(pathArg);

		final CommandLineParser parser = new BasicParser();

		try {
			final CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("f") || cmd.hasOption("files")) {
				System.out.println("Files");
			} else if (cmd.hasOption("p") || cmd.hasOption("path")) {
				File                  tempFile;
				final String[]        values = cmd.getOptionValues("p");
				final ArrayList<File> paths  = new ArrayList<File>();
				for (final String string : values) {
					tempFile = new File(string);
					if (tempFile.exists() && tempFile.canWrite()) {
						paths.add(tempFile);
					}
				}

				this.startPath(paths, null);
			}

		} catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		new MainCommandline(args);
	}

	private void startPath(final ArrayList<File> paths, final String filter) {

	}

}
