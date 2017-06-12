package org.iMage.geometrify.cli;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.iMage.geometrify.IPrimitiveFilter;
import org.iMage.geometrify.RandomPointGenerator;
import org.iMage.geometrify.TrianglePictureFilter;

/**
 * This class parses all command line parameters and applies the primitive
 * picture reproduction.
 *
 * @author Dominic Ziegler, Sebastian Weigelt
 * @version 1.1
 */
public class App {
	private static final String CMD_OPTION_INPUT_FILE = "i";
	private static final String CMD_OPTION_OUTPUT_FILE = "o";
	private static final String CMD_OPTION_NUM_ITERATIONS = "n";
	private static final String CMD_OPTION_NUM_SAMPLES = "s";

	/*
	 * Private constructor: App is not to be instantiated from outside
	 */
	private App() {
	}

	/**
	 * Main entry method. Reads the command line arguments and runs the
	 * requested filter.
	 *
	 * @param args
	 *            command line arguments (parsed with commons-cli)
	 */
	public static void main(String[] args) {
		CommandLine cmd = null;
		try {
		cmd = doCommandLineParsing(args);
		} catch (ParseException exception) {
		System.err.println("Wrong cl args given: " + exception.getMessage());
		System.exit(1);
		}
		BufferedImage sourceImage = null;
		try {
		// read image
		File sourceFile = new File(cmd.getOptionValue(CMD_OPTION_INPUT_FILE));
		sourceImage = ImageIO.read(sourceFile);
		} catch (IOException e) {
		System.err.println("Cannot read input file: ");
		e.printStackTrace();
		System.exit(1);
		}
		BufferedImage image = sourceImage;
		int iterations = cmd.hasOption(CMD_OPTION_NUM_ITERATIONS) ?
				Integer.parseInt(cmd.getOptionValue(CMD_OPTION_NUM_ITERATIONS)) : 100;
				int samples = cmd.hasOption(CMD_OPTION_NUM_SAMPLES) ?
				Integer.parseInt(cmd.getOptionValue(CMD_OPTION_NUM_SAMPLES)) : 50;
				RandomPointGenerator pointGen = new RandomPointGenerator(image.getWidth(),
				image.getHeight());
				IPrimitiveFilter filter = new TrianglePictureFilter(pointGen);
				BufferedImage output = filter.apply(image, iterations, samples);
				try {
				File destFile = new File(cmd.getOptionValue(CMD_OPTION_OUTPUT_FILE));
				ImageIO.write(output, "png", destFile);
				} catch (IOException e) {
				System.err.println("Cannot write output image: ");
				e.printStackTrace();
				System.exit(1);
				}
				System.exit(0);
				}

	/**
	 * Parse and check command line arguments
	 *
	 * @param args
	 *            command line arguments given by the user
	 * @return CommandLine object encapsulating all options
	 * @throws ParseException
	 *             iff wrong command line parameters or arguments are given
	 */
	private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
		Options options = new Options();
		Option opt;

		/*
		 * Define command line options and arguments
		 */
		opt = new Option(CMD_OPTION_INPUT_FILE, "in-file", true, "Input file");
		opt.setRequired(true);
		opt.setType(String.class);
		options.addOption(opt);

		opt = new Option(CMD_OPTION_OUTPUT_FILE, "out-file", true, "Output file");
		opt.setRequired(true);
		opt.setType(String.class);
		options.addOption(opt);

		opt = new Option(CMD_OPTION_NUM_ITERATIONS, "num-iterations", true, "Number of iterations");
		opt.setRequired(false);
		opt.setType(Integer.class);
		options.addOption(opt);

		opt = new Option(CMD_OPTION_NUM_SAMPLES, "num-samples", true, "Number of samples per iteration");
		opt.setRequired(false);
		opt.setType(Integer.class);
		options.addOption(opt);

		// Parse the command line arguments
		CommandLineParser parser = new DefaultParser();
		return parser.parse(options, args);
	}
}
