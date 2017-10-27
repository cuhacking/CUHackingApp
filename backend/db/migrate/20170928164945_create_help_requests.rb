class CreateHelpRequests < ActiveRecord::Migration[5.1]
  def change
    create_table :help_requests do |t|
    	t.belongs_to :user, optional: true
      
      t.string :location
      t.string :problem
      t.string :mentors
      t.string :status

      t.timestamps
    end
  end
end
