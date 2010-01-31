package wordle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DoPolicy
{
	public static void main(final String[] args)
	{
		final String home = System.getProperty("user.home");
		File homeDir = home == null ? null : new File(home);
		if (home == null || !homeDir.exists())
		{
			final JFileChooser jfc = new JFileChooser("/users");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
				return;
			homeDir = jfc.getSelectedFile();
		}
		if (!homeDir.exists())
		{
			JOptionPane.showMessageDialog(null, homeDir + " does not exist.",
					"Fatal Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		final File policyFile = new File(homeDir, ".java.policy");
		final StringBuilder policyBuilder = new StringBuilder();
		if (policyFile.exists())
		{
			final int r = JOptionPane.showConfirmDialog(null, "Modify the existing file "
					+ policyFile + " to permit Wordle to print?",
					"Modify Existing Policy File?", JOptionPane.OK_CANCEL_OPTION);
			if (r != JOptionPane.OK_OPTION)
				return;
			try
			{
				final BufferedReader in = new BufferedReader(new FileReader(policyFile));
				try
				{
					String line;
					while ((line = in.readLine()) != null)
						policyBuilder.append(line).append("\n");
				}
				finally
				{
					in.close();
				}
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), "Fatal Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else
		{
			final int r = JOptionPane.showConfirmDialog(null, "Create the file "
					+ policyFile + "\nin order to permit Wordle to print?",
					"Modify Existing Policy File?", JOptionPane.OK_CANCEL_OPTION);
			if (r != JOptionPane.OK_OPTION)
				return;
		}
		try
		{
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					DoPolicy.class.getResourceAsStream("policy.txt")));
			try
			{
				String line;
				while ((line = in.readLine()) != null)
					policyBuilder.append(line).append("\n");
			}
			finally
			{
				in.close();
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fatal Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try
		{
			final Writer out = new FileWriter(policyFile);
			try
			{
				out.write(policyBuilder.toString());
				out.flush();
			}
			finally
			{
				out.close();
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fatal Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane
				.showMessageDialog(
						null,
						"Please close any open browser windows, and visit Wordle to try and print.",
						"Mission Accomplished", JOptionPane.INFORMATION_MESSAGE);
	}
}
